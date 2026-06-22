package com.porfolio.online_store.model.order;

import com.porfolio.online_store.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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
    @Enumerated(EnumType.STRING) //for chatGPT orderStatus are PENDING,SHIPPED,DELIVERED,CANCELLED
    private OrderStatus status;
    private String shippingAddress;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalPrice;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
    @Column(updatable = false)
    private Timestamp shippedAt;
    @Column(updatable = false)
    private Timestamp deliveredAt;
    private String cancellationReason;
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
}
