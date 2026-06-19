package com.porfolio.online_store.dto.user;


import com.porfolio.online_store.dto.cart.CartDto;
import com.porfolio.online_store.model.user.UserRole;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private UserRole role;
    private CartDto cart;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isActive;
}
