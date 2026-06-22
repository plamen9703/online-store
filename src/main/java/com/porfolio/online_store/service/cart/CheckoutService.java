package com.porfolio.online_store.service.cart;

import com.porfolio.online_store.dto.cart.CartDto;
import com.porfolio.online_store.dto.cart.CartItemDto;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.mapper.product.ProductMapper;
import com.porfolio.online_store.mapper.user.UserMapper;
import com.porfolio.online_store.model.order.OrderItem;
import com.porfolio.online_store.model.product.Product;
import com.porfolio.online_store.model.user.User;
import com.porfolio.online_store.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final OrderService orderService;


    public Map<UserDto, List<CartItemDto>> getUsersCheckoutBySeller(UserDto userDto){
        CartDto cart = cartService.getUsersCart(userDto);
        Map<UserDto, List<CartItemDto>> usersOrdersCheckoutBySeller = new HashMap<>();

        for (CartItemDto item : cart.getItems()) {
            if(item.getQuantity() == 0){
                continue;
            }
            UserDto owner = item.getProduct().getOwner();
            List<CartItemDto> userProductList = usersOrdersCheckoutBySeller.getOrDefault(owner, new ArrayList<>());
            userProductList.add(item);
            usersOrdersCheckoutBySeller.putIfAbsent(owner, userProductList);
        }
        return usersOrdersCheckoutBySeller;
    }

    @Transactional
    public void convertUsersCartToOrders(UserDto user, String shippingAddress){
        Map<UserDto, List<CartItemDto>> usersCheckoutBySeller = getUsersCheckoutBySeller(user);

        User customer = UserMapper.toUserEntity(user);

        usersCheckoutBySeller.forEach((key, value)->{
            BigDecimal totalPrice = value.stream()
                    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal::add)
                    .orElseGet(()-> BigDecimal.valueOf(0));
            List<OrderItem> items = value.stream()
                    .map(item ->
                    {
                        Product product = ProductMapper.toEntity(item.getProduct());
                        return OrderItem.builder()
                                .product(product)
                                .productNameAtPurchase(product.getName())
                                .quantity(item.getQuantity())
                                .priceAtPurchase(product.getPrice())
                                .build();
                    })
                    .toList();
            User seller = User.builder()
                    .id(key.getId())
                    .username(key.getUsername())
                    .email(key.getEmail())
                    .firstName(key.getFirstName())
                    .lastName(key.getLastName())
                    .build();
            orderService.createNewOrder(
                    customer,
                    seller,
                    shippingAddress,
                    totalPrice,
                    items
                    );
        });
        cartService.checkoutUsersCart(user.getId());
    }


}
