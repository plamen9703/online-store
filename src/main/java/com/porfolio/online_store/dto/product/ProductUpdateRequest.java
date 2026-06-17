package com.porfolio.online_store.dto.product;

import com.porfolio.online_store.model.product.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


import static com.porfolio.online_store.constants.ApplicationConstants.*;

@Builder
@Data
public class ProductUpdateRequest {
    @Size(min = 3, max = 50, message = PRODUCT_NAME_SIZE_ERROR_MESSAGE)
    @NotBlank(message = PRODUCT_NAME_ERROR_MESSAGE)
    private String name;
    @Size(max = 1000, message = PRODUCT_DESCRIPTION_ERROR_MESSAGE)
    private String description;
    @Min(value = 0, message = PRODUCT_PRICE_ERROR_MESSAGE)
    private double price;
    private String imageUrl;
    private ProductCategory category;
    @Min(value = 0, message = PRODUCT_STOCK_QUANTITY_ERROR_MESSAGE)
    private int stockQuantity;
}
