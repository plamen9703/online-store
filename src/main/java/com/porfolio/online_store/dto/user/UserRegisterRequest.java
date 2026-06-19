package com.porfolio.online_store.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.porfolio.online_store.constants.ApplicationConstants.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRegisterRequest {
    @NotEmpty(message = USERNAME_SIZE_ERROR_MESSAGE)
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    @NotEmpty(message = EMAIL_EMPTY_ERROR_MESSAGE)
    @Email(message = INVALID_EMAIL_ERROR_MESSAGE)
    private String email;
    @NotEmpty(message = PASSWORD_EMPTY_ERROR_MESSAGE)
    @Size(min = 6, message = PASSWORD_SIZE_ERROR_MESSAGE)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = PASSWORD_PATTERN_ERROR_MESSAGE)
    private String password;
    private String imageUrl;
    @Size(max = 20, message = FIRST_NAME_SIZE_ERROR_MESSAGE)
    private String firstName;
    @Size(max = 20, message = LAST_NAME_SIZE_ERROR_MESSAGE)
    private String lastName;
}
