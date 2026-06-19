package com.porfolio.online_store.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class AddProductRequest {
    @NotNull(message = "Product id can not be null.")
    private UUID productId;
}
