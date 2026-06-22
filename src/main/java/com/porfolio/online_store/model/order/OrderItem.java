package com.porfolio.online_store.model.order;

import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import static com.porfolio.online_store.constants.ApplicationConstants.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    private String productNameAtPurchase;
    @Min(value = 0, message = ORDER_ITEM_QUANTITY_ERROR_MESSAGE)
    private int quantity;
    @DecimalMin(value = "0.0", message = ORDER_ITEM_PRICE_AT_PURCHASE_ERROR_MESSAGE)
    private BigDecimal priceAtPurchase;
}
