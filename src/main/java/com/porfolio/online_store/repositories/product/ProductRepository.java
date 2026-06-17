package com.porfolio.online_store.repositories.product;

import com.porfolio.online_store.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

     Page<Product> findByOwnerIdNot(UUID ownerId, Pageable pageable);

    Page<Product> findByOwnerId(UUID ownerId, Pageable pageable);


}
