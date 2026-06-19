package com.porfolio.online_store.mapper.oder;

import com.porfolio.online_store.dto.order.OrderItemDto;
import com.porfolio.online_store.mapper.product.ProductMapper;
import com.porfolio.online_store.model.order.OrderItem;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderItemMapper {

    public static OrderItemDto toDto(OrderItem orderItem){
        if(orderItem == null) {
            return null;
        }
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .product(ProductMapper.toDto(orderItem.getProduct()))
                .priceAtPurchase(orderItem.getProduct().getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public static OrderItem toEntity(OrderItemDto orderItem) {
        if(orderItem == null) {
            return null;
        }
        return OrderItem.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .product(ProductMapper.toEntity(orderItem.getProduct()))
                .build();
    }
}
