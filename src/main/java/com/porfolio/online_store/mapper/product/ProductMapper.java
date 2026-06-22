package com.porfolio.online_store.mapper.product;

import com.porfolio.online_store.dto.product.ProductCreationRequest;
import com.porfolio.online_store.dto.product.ProductDto;
import com.porfolio.online_store.dto.product.ProductUpdateRequest;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.mapper.user.UserMapper;
import com.porfolio.online_store.model.product.Product;
import com.porfolio.online_store.model.user.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductMapper {

        public static ProductDto toDto(Product product) {
            if (product == null) {
                return null;
            }
            User productOwner = product.getOwner();
            UserDto owner = UserDto.builder()
                    .id(productOwner.getId())
                    .username(productOwner.getUsername())
                    .email(productOwner.getEmail())
                    .firstName(productOwner.getFirstName())
                    .lastName(productOwner.getLastName())
                    .email(productOwner.getEmail())
                    .build();
            return ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .imageUrl(product.getImageUrl())
                    .category(product.getCategory())
                    .stockQuantity(product.getStockQuantity())
                    .owner(owner)
                    .createdAt(product.getCreatedAt())
                    .updatedAt(product.getUpdatedAt())
                    .build();
        }

        public static Product toEntity(ProductCreationRequest request) {
            if (request == null) {
                return null;
            }
            return Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .imageUrl(request.getImageUrl())
                    .category(request.getCategory())
                    .stockQuantity(request.getStockQuantity())
                    .build();
        }

        public static Product toEntity(ProductUpdateRequest request) {
            if (request == null) {
                return null;
            }
            return Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .imageUrl(request.getImageUrl())
                    .category(request.getCategory())
                    .stockQuantity(request.getStockQuantity())
                    .build();
        }


        public static Product toEntity(ProductDto product) {
            if (product == null) {
                return null;
            }
            User owner = User.builder()
                    .id(product.getId())
                    .build();
            return Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .imageUrl(product.getImageUrl())
                    .category(product.getCategory())
                    .stockQuantity(product.getStockQuantity())
                    .owner(owner)
                    .build();
        }
}
