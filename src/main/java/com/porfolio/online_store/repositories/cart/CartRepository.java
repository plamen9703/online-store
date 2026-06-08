package com.porfolio.online_store.repositories.cart;

import com.porfolio.online_store.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}
