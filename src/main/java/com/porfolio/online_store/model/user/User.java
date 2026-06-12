package com.porfolio.online_store.model.user;

import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.order.Order;
import com.porfolio.online_store.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
    @Column(nullable = false)
    private boolean isActive;
    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<Product> products;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<Order> orders;
    @OneToMany(mappedBy = "seller",fetch = FetchType.LAZY)
    private List<Order> sales;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

}
