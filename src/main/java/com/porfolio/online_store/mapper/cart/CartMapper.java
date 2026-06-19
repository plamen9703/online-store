package com.porfolio.online_store.mapper.cart;

import com.porfolio.online_store.dto.cart.CartDto;
import com.porfolio.online_store.dto.cart.CartItemDto;
import com.porfolio.online_store.mapper.user.UserMapper;
import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.cart.CartItem;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CartMapper {

    public static CartDto toDto(Cart cart){
        if(cart == null){
            return null;
        }

        List<CartItemDto> orderItems = cart.getItems().stream().map(CartItemMapper::toDto).toList();

        return CartDto.builder()
                .id(cart.getId())
                .user(UserMapper.toUserDto(cart.getUser()))
                .items(orderItems)
                .build();
    }

    public static Cart toEntity(CartDto cart){
        if(cart == null){
            return null;
        }

        List<CartItem> orderItems = cart.getItems().stream().map(CartItemMapper::toEntity).toList();

        return Cart.builder()
                .id(cart.getId())
                .user(UserMapper.toUserEntity(cart.getUser()))
                .items(orderItems)
                .build();
    }
}
