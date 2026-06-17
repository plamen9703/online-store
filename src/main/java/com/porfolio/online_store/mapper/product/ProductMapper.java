package com.porfolio.online_store.mapper.product;

import com.porfolio.online_store.dto.product.ProductCreationRequest;
import com.porfolio.online_store.dto.product.ProductDto;
import com.porfolio.online_store.mapper.user.UserMapper;
import com.porfolio.online_store.model.product.Product;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductMapper {

        public static ProductDto toDto(Product product) {
            if (product == null) {
                return null;
            }
            return ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .imageUrl(product.getImageUrl())
                    .category(product.getCategory())
                    .stockQuantity(product.getStockQuantity())
                    .owner(UserMapper.toUserDto(product.getOwner()))
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
}
