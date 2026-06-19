package com.porfolio.online_store.web.cart;

import com.porfolio.online_store.dto.cart.AddProductRequest;
import com.porfolio.online_store.dto.cart.CartUpdateRequest;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.service.cart.CartService;
import com.porfolio.online_store.service.user.UserSessionLoaderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserSessionLoaderService userSessionLoaderService;

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(
            @Valid @RequestBody AddProductRequest productRequest,
            HttpSession session) {

        UserDto user = userSessionLoaderService.loadUserFromSession(session);


        cartService.addProductToCart(
                productRequest.getProductId(),
                user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCart(
            @Valid @RequestBody CartUpdateRequest request,
            HttpSession session) {

        UserDto user = userSessionLoaderService.loadUserFromSession(session);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login first.");
        }

        try {
            cartService.updateUsersCart(
                    request.getProductId(),
                    user,
                    request.getQuantity());

            return ResponseEntity.ok().build();

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ModelAndView getUsersCart(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if(user== null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        BigDecimal totalPrice = cartService.getCartTotalPrice(user.getCart());

        modelAndView.setViewName("cart");
        modelAndView.addObject("user", user);
        modelAndView.addObject("cart", user.getCart());
        modelAndView.addObject("totalPrice", totalPrice);
        return modelAndView;
    }

    @GetMapping("/totalprice")
    public ResponseEntity<BigDecimal> getUsersCartTotalPrice(HttpSession session){
        UserDto user = userSessionLoaderService.loadUserFromSession(session);

        BigDecimal totalPrice = cartService.getCartTotalPrice(user.getCart());

        return ResponseEntity.ok(totalPrice);
    }

}