package com.porfolio.online_store.service.product;

import com.porfolio.online_store.dto.product.ProductCreationRequest;
import com.porfolio.online_store.dto.product.ProductDto;
import com.porfolio.online_store.dto.product.ProductUpdateRequest;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.mapper.product.ProductMapper;
import com.porfolio.online_store.mapper.user.UserMapper;
import com.porfolio.online_store.model.product.Product;
import com.porfolio.online_store.model.user.UserRole;
import com.porfolio.online_store.repositories.product.ProductRepository;
import com.porfolio.online_store.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.porfolio.online_store.constants.ApplicationConstants.PRODUCT_PAGE_SIZE;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto createProduct(UserDto user, ProductCreationRequest request) {
        Product product = ProductMapper.toEntity(request);
        product.setOwner(UserMapper.toUserEntity(user));
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    public void updateProduct(String productId, ProductUpdateRequest request, UserDto updatedBy) {
        Product product = productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new RuntimeException("Product with this id does not exist!"));

        if (!product.getOwner().getId().equals(updatedBy.getId())
                && updatedBy.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("User does not have permission to update this product.");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(request.getCategory());
        product.setStockQuantity(request.getStockQuantity());

        productRepository.save(product);
    }


    public ProductDto getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with this id does not exist!"));
        return ProductMapper.toDto(product);
    }

    public Page<ProductDto> getProductsForUser(String userId, Integer page){
        Pageable pageable =
                PageRequest.of(page, PRODUCT_PAGE_SIZE, Sort.by("createdAt").descending());
        return productRepository
                .findByOwnerIdNot(UUID.fromString(userId), pageable)
                .map(ProductMapper::toDto);
    }

    public Page<ProductDto> getUsersProducts(String userId, Integer page){
        Pageable pageable =
                PageRequest.of(page, PRODUCT_PAGE_SIZE, Sort.by("createdAt").descending());

        return productRepository
                .findByOwnerId(UUID.fromString(userId), pageable)
                .map(ProductMapper::toDto);
    }

    public void deleteProduct(String productId, UserDto requestedBy) {
        Product product = productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new RuntimeException("Product with this id does not exist!"));

        if (!product.getOwner().getId().equals(requestedBy.getId())
                && requestedBy.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("User does not have permission to update this product.");
        }
        productRepository.delete(product);
    }

    public Page<ProductDto> getProductPage(Integer page){
        PageRequest pageRequest = PageRequest.of(page, PRODUCT_PAGE_SIZE, Sort.by("createdAt").descending());


        return productRepository.findAll(pageRequest).map(ProductMapper::toDto);
    }

    public boolean exists(UUID productId){
        return productRepository.existsById(productId);
    }
}
