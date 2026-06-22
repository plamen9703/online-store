package com.porfolio.online_store.repositories.cart;

import com.porfolio.online_store.model.cart.CartItem;
import com.porfolio.online_store.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    List<CartItem> findAllByProductId(UUID productId);
}
