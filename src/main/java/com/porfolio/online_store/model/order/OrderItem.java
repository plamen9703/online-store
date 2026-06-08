package com.porfolio.online_store.model.order;

import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
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
    @Positive
    private int quantity;
    @Positive
    private double priceAtPurchase;
    @OneToOne(fetch = FetchType.LAZY)
    private Cart cart;
}
