package com.porfolio.online_store.web.cart;

import com.porfolio.online_store.dto.cart.CartItemDto;
import com.porfolio.online_store.dto.product.ProductDto;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.service.cart.CheckoutService;
import com.porfolio.online_store.service.user.UserSessionLoaderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final UserSessionLoaderService userSessionLoaderService;
    private final CheckoutService checkoutService;

    @GetMapping
    public ModelAndView getCheckoutPage(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        Map<UserDto, List<CartItemDto>> ordersBySeller = checkoutService.getUsersCheckoutBySeller(user);

        modelAndView.setViewName("checkout");
        modelAndView.addObject("ordersBySeller", ordersBySeller);
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
