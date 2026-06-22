package com.porfolio.online_store.model.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CartItemRemoveRequest {
    @NotNull
    private UUID productId;
}
