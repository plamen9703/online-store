package com.porfolio.online_store.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartUpdateRequest {

    @NotNull(message = "Product id cannot be null!")
    private UUID productId;
//    @Min(value = 0, message = "Quantity of product in cart can not be less than 0.")
    private int quantity;
}
