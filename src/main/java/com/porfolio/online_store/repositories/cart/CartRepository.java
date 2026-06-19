package com.porfolio.online_store.repositories.cart;

import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    Optional<Cart> findByUserId(UUID userId);

    @Query("""
    SELECT SUM(ci.quantity * p.price)
    FROM CartItem ci
    JOIN ci.product p
    WHERE ci.cart.id = :cartId
""")
    BigDecimal getCartTotalPrice(@Param("cartId") UUID cartId);
}
