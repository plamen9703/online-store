package com.porfolio.online_store.dto.order;

import com.porfolio.online_store.dto.cart.CartDto;
import com.porfolio.online_store.dto.product.ProductDto;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

import static com.porfolio.online_store.constants.ApplicationConstants.*;

@Builder
@Data
public class OrderItemDto {
    private UUID id;
    private OrderDto order;
    private ProductDto product;
    @Min(value = 0, message = ORDER_ITEM_QUANTITY_ERROR_MESSAGE)
    private int quantity;
    private BigDecimal priceAtPurchase;
}
