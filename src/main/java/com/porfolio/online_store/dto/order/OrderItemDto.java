package com.porfolio.online_store.dto.order;

import com.porfolio.online_store.dto.product.ProductDto;
import com.porfolio.online_store.model.order.Order;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class OrderItemDto {

    private UUID id;
    private Order order;
    private ProductDto product;
    private String productNameAtPurchase;
    private int quantity;
    private BigDecimal priceAtPurchase;
}
