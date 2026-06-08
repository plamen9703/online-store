package com.porfolio.online_store.model.cart;

import com.porfolio.online_store.model.order.OrderItem;
import com.porfolio.online_store.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Builder
@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;
    @Enumerated(EnumType.STRING)
    private StatusCart status;
    @Transient
    private double totalPrice;
    @Column(nullable = false)
    private String deliveryAddress;

}
