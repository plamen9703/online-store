package com.porfolio.online_store.dto.product;

import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.model.product.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private ProductCategory category;
    private int stockQuantity;
    private UserDto owner;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
