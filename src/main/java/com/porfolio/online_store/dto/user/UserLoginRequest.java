package com.porfolio.online_store.dto.user;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserLoginRequest {
    @Size(min = 3, message = "User login must be at least 3 characters")
    private String login;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
