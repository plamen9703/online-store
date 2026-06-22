package com.porfolio.online_store.service.order;

import com.porfolio.online_store.dto.order.OrderDto;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.mapper.oder.OrderMapper;
import com.porfolio.online_store.model.order.Order;
import com.porfolio.online_store.model.order.OrderCancelRequest;
import com.porfolio.online_store.model.order.OrderItem;
import com.porfolio.online_store.model.order.OrderStatus;
import com.porfolio.online_store.model.product.Product;
import com.porfolio.online_store.model.user.User;
import com.porfolio.online_store.repositories.oder.OrderRepository;
import com.porfolio.online_store.service.cart.CartItemService;
import com.porfolio.online_store.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartItemService cartItemService;

    public static final Supplier<RuntimeException> ORDER_NOT_FOUND = () -> new RuntimeException("Order not found.");

    @Transactional
    public OrderDto createNewOrder(
        User customer,
        User seller,
        String shippingAddress,
        BigDecimal totalPrice,
        List<OrderItem> orderItems){
        Order order = Order.builder()
                .customer(customer)
                .seller(seller)
                .status(OrderStatus.PENDING)
                .shippingAddress(shippingAddress)
                .totalPrice(totalPrice)
                .build();

        orderItems.forEach(order::addItem);
        Order saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    public List<OrderDto> getUsersOrdersOwn(UserDto user) {
        List<Order> orders = orderRepository.findAllByCustomerId(user.getId());
        return orders.stream().map(OrderMapper::toDto).toList();
    }

    public List<OrderDto> getUsersOrdersRecieved(UserDto user) {
        List<Order> orders = orderRepository.findAllBySellerId(user.getId());
        return orders.stream().map(OrderMapper::toDto).toList();
    }

    public OrderDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(ORDER_NOT_FOUND);
        return OrderMapper.toDto(order);
    }

    @Transactional
    public void shipOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(ORDER_NOT_FOUND);

        if(order.getStatus().equals(OrderStatus.PENDING)){
            order.getItems().forEach(item-> {
                Product product = item.getProduct();
                int newProductQuantity = product.getStockQuantity() - item.getQuantity();
                productService.updateProductQuantity(product.getId(), newProductQuantity);
                cartItemService.updateProductCartItems(product.getId());
                cartItemService.updateProductCartItems(product.getId());
            });
            order.setShippedAt(Timestamp.from(Instant.now()));
        }

        order.setStatus(OrderStatus.SHIPPED);
        order.setUpdatedAt(Timestamp.from(Instant.now()));
    }

    @Transactional
    public void completeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(ORDER_NOT_FOUND);
        if(!order.getStatus().equals(OrderStatus.SHIPPED)){
            throw new RuntimeException("Can not complete order that has not been shipped!");
        }

        order.setDeliveredAt(Timestamp.from(Instant.now()));
        order.setStatus(OrderStatus.DELIVERED);
        order.setUpdatedAt(Timestamp.from(Instant.now()));
    }

    @Transactional
    public void cancelOrder(OrderCancelRequest orderCancelRequest){
        Order order = orderRepository.findById(orderCancelRequest.getId()).orElseThrow(ORDER_NOT_FOUND);
        if(order.getStatus().equals(OrderStatus.DELIVERED)){
            throw new RuntimeException("Order status can not be changed from Delivered to Canceled!");
        }
        if(order.getStatus().equals(OrderStatus.CANCELLED)){
            throw new RuntimeException("Order is already canceled.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancellationReason(orderCancelRequest.getCancellationReason());
        order.setUpdatedAt(Timestamp.from(Instant.now()));
    }
}
