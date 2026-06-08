package com.porfolio.online_store.repositories.product;

import com.porfolio.online_store.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
