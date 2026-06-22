package com.porfolio.online_store.service.cart;

import com.porfolio.online_store.model.cart.CartItem;
import com.porfolio.online_store.repositories.cart.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;


    @Transactional
    public void updateProductCartItems(UUID productId){
        List<CartItem> items = cartItemRepository.findAllByProductId(productId);

        items.forEach( item ->{
            if(item.getQuantity()>item.getProduct().getStockQuantity()){
                item.setQuantity(item.getProduct().getStockQuantity());
            }
        });
    }
}
