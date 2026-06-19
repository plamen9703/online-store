package com.porfolio.online_store.dto.cart;

import com.porfolio.online_store.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class CartDto {

    private UUID id;
    private UserDto user;
    private List<CartItemDto> items;
    private double totalPrice;
}
