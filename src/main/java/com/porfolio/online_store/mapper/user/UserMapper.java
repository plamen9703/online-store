package com.porfolio.online_store.mapper.user;

import com.porfolio.online_store.dto.cart.CartDto;
import com.porfolio.online_store.dto.cart.CartItemDto;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.dto.user.UserRegisterRequest;
import com.porfolio.online_store.mapper.cart.CartItemMapper;
import com.porfolio.online_store.mapper.cart.CartMapper;
import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.user.User;
import com.porfolio.online_store.model.user.UserRole;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class UserMapper {


    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }


        Cart cart = user.getCart();
        List<CartItemDto> cartItems = cart.getItems().stream().map(CartItemMapper::toDto).toList();
        CartDto cartDto = CartDto.builder()
                .id(cart.getId())
                .items(cartItems)
                .build();

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .imageUrl(user.getImageUrl())
                .role(user.getRole())
                .cart(cartDto)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isActive(user.isActive())
                .build();
    }


    public static User toUserEntity(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        return User.builder()
                .username(userRegisterRequest.getUsername())
                .email(userRegisterRequest.getEmail())
                .password(userRegisterRequest.getPassword())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .role(UserRole.CUSTOMER)
                .imageUrl(userRegisterRequest.getImageUrl())
                .isActive(true)
                .build();
    }

    public static User toUserEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        Cart cart = Cart.builder()
                .id(userDto.getCart().getId())
                .items(userDto.getCart().getItems().stream().map(CartItemMapper::toEntity).toList())
                .build();
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .imageUrl(userDto.getImageUrl())
                .role(userDto.getRole())
                .cart(cart)
                .createdAt(userDto.getCreatedAt())
                .updatedAt(userDto.getUpdatedAt())
                .isActive(userDto.isActive())
                .build();
    }
}
