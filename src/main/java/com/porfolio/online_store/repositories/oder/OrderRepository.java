package com.porfolio.online_store.repositories.oder;

import com.porfolio.online_store.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
