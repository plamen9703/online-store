package com.porfolio.online_store.dto.cart;

import com.porfolio.online_store.dto.product.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CartItemDto {
    private UUID id;
    private ProductDto product;
    private int quantity;
    private CartDto cart;
}
