package com.porfolio.online_store.mapper.user;

import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.dto.user.UserRegisterRequest;
import com.porfolio.online_store.model.user.User;
import com.porfolio.online_store.model.user.UserRole;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {


    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .imageUrl(user.getImageUrl())
                .role(user.getRole())
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
}
