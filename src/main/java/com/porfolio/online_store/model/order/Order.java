package com.porfolio.online_store.model.order;

import com.porfolio.online_store.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
@Builder
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User customer;
    @ManyToOne(fetch = FetchType.LAZY)
    private User seller;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(nullable = false)
    @NotEmpty
    private String shippingAddress;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
}
