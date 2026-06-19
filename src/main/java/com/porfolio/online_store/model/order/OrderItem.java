package com.porfolio.online_store.model.order;

import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import static com.porfolio.online_store.constants.ApplicationConstants.*;

@Builder
@Data
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Min(value = 0, message = ORDER_ITEM_QUANTITY_ERROR_MESSAGE)
    private int quantity;
    @Min(value = 0, message = ORDER_ITEM_PRICE_AT_PURCHASE_ERROR_MESSAGE)
    private double priceAtPurchase;
}
