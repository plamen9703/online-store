package com.porfolio.online_store.dto.user;

import jakarta.validation.constraints.Email;
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
public class UserUpdateRequest {
    @Size(max=20, message = FIRST_NAME_SIZE_ERROR_MESSAGE)
    private String firstName;
    @Size(max=20, message = LAST_NAME_SIZE_ERROR_MESSAGE)
    private String lastName;
    private String imageUrl;

}
