package com.porfolio.online_store.repositories.oder;

import com.porfolio.online_store.model.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
