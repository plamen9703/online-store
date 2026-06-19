package com.porfolio.online_store.mapper.cart;

import com.porfolio.online_store.dto.cart.CartDto;
import com.porfolio.online_store.dto.cart.CartItemDto;
import com.porfolio.online_store.dto.product.ProductDto;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.mapper.product.ProductMapper;
import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.cart.CartItem;
import com.porfolio.online_store.model.product.Product;
import com.porfolio.online_store.model.user.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CartItemMapper {

    public static CartItemDto toDto(CartItem cartItem){
        if(cartItem == null){
            return null;
        }

        Product cartItemProduct = cartItem.getProduct();
        UserDto productOwner = UserDto.builder()
                .id(cartItemProduct.getOwner().getId())
                .build();
        ProductDto product = ProductDto.builder()
                .id(cartItemProduct.getId())
                .name(cartItemProduct.getName())
                .price(cartItemProduct.getPrice())
                .stockQuantity(cartItemProduct.getStockQuantity())
                .imageUrl(cartItemProduct.getImageUrl())
                .category(cartItemProduct.getCategory())
                .owner(productOwner)
                .build();
        return CartItemDto.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .cart(CartDto.builder().id(cartItem.getCart().getId()).build())
                .product(product)
                .build();
    }

    public static CartItem toEntity(CartItemDto cartItem){
        if(cartItem == null){
            return null;
        }
        ProductDto cartItemProduct = cartItem.getProduct();
        User productOwner = User.builder()
                .id(cartItemProduct.getOwner().getId())
                .build();
        Product product = Product.builder()
                .id(cartItemProduct.getId())
                .name(cartItemProduct.getName())
                .price(cartItemProduct.getPrice())
                .stockQuantity(cartItemProduct.getStockQuantity())
                .imageUrl(cartItemProduct.getImageUrl())
                .category(cartItemProduct.getCategory())
                .owner(productOwner)
                .build();
        return CartItem.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .product(product)
                .cart(Cart.builder().id(cartItem.getCart().getId()).build())
                .build();
    }

}
