package com.porfolio.online_store.mapper.oder;

import com.porfolio.online_store.dto.order.OrderDto;
import com.porfolio.online_store.dto.order.OrderItemDto;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.mapper.user.UserMapper;
import com.porfolio.online_store.model.order.Order;
import com.porfolio.online_store.model.order.OrderItem;
import com.porfolio.online_store.model.user.User;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class OrderMapper {

    public static OrderDto toDto(Order order){
        if(order == null){
            return null;
        }

        List<OrderItemDto> items = order.getItems().stream().map(OrderItemMapper::toDto).toList();

        UserDto customer = UserDto.builder()
                .id(order.getCustomer().getId())
                .username(order.getCustomer().getUsername())
                .build();
        UserDto seller = UserDto.builder()
                .id(order.getSeller().getId())
                .username(order.getSeller().getUsername())
                .build();
        return OrderDto.builder()
                .id(order.getId())
                .customer(customer)
                .seller(seller)
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .totalPrice(order.getTotalPrice())
                .items(items)
                .shippedAt(order.getShippedAt())
                .deliveredAt(order.getDeliveredAt())
                .cancellationReason(order.getCancellationReason())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public static Order toEntity(OrderDto order){
        if(order == null){
            return null;
        }

        List<OrderItem> items = order.getItems().stream().map(OrderItemMapper::toEntity).toList();

        User customer = User.builder()
                .id(order.getCustomer().getId())
                .build();
        User seller = User.builder()
                .id(order.getSeller().getId())
                .build();
        return Order.builder()
                .id(order.getId())
                .customer(customer)
                .seller(seller)
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .totalPrice(order.getTotalPrice())
                .items(items)
                .shippedAt(order.getShippedAt())
                .deliveredAt(order.getDeliveredAt())
                .cancellationReason(order.getCancellationReason())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
