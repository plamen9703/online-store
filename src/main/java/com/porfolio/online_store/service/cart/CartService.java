package com.porfolio.online_store.service.cart;

import com.porfolio.online_store.dto.cart.CartDto;
import com.porfolio.online_store.dto.product.ProductDto;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.mapper.cart.CartMapper;
import com.porfolio.online_store.model.cart.Cart;
import com.porfolio.online_store.model.cart.CartItem;
import com.porfolio.online_store.model.product.Product;
import com.porfolio.online_store.model.user.User;
import com.porfolio.online_store.repositories.cart.CartRepository;
import com.porfolio.online_store.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;


    public Cart createNewCart(User user){
        if(user.getCart() !=null ){
            throw new RuntimeException("User [%s] already has a cart!".formatted(user.getId()));
        }

        Cart cart = Cart.builder()
                .user(user)
                .build();
        return cartRepository.save(cart);
    }

    @Transactional
    public void addProductToCart(UUID productId, UserDto user) {

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow();
        ProductDto product = productService.getProductById(productId);

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) {

            int quantity = product.getStockQuantity() == 0 ? 0 : 1;

            cart.getItems().add(
                    CartItem.builder()
                            .cart(cart)
                            .product(Product.builder().id(productId).build())
                            .quantity(quantity)
                            .build()
            );

        } else {

            updateUsersCart(productId, user, item.getQuantity()+1);
        }
    }

    @Transactional
    public void updateUsersCart(UUID productId, UserDto user, int quantity){
        ProductDto product = productService.getProductById(productId);
        if(product.getOwner().getId().equals(user.getId())){
            throw new RuntimeException("Can not add product owned by user to the users cart.");
        }

        if(product.getStockQuantity() < quantity){
            throw new RuntimeException("Product stock quantity inceficient to add to cart.");
        }

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow();
        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if(item == null){
            CartItem newCartItem = CartItem.builder()
                    .product(Product.builder().id(productId).build())
                    .cart(Cart.builder().id(cart.getId()).build())
                    .quantity(quantity)
                    .build();
            cart.getItems().add(newCartItem);
            return;
        }

        item.setQuantity(quantity);

    }

    public BigDecimal getCartTotalPrice(CartDto cart){
        return cartRepository.getCartTotalPrice(cart.getId());
    }

    public CartDto getUsersCart(UserDto userDto){
        Cart cart = cartRepository.findByUserId(userDto.getId()).orElseThrow(() -> new RuntimeException("Users cart not found."));
        return CartMapper.toDto(cart);
    }
    @Transactional
    public void checkoutUsersCart(UUID userid){
        Cart cart = cartRepository.findByUserId(userid).orElseThrow(()->new RuntimeException("Users cart not found."));

        cart.getItems().removeIf(item -> item.getQuantity() > 0);

    }

    @Transactional
    public void removeFromUsersCart(UUID productId, UserDto user) {
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow();
        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        cart.getItems().remove(item);
    }
}
