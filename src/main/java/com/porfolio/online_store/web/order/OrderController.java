package com.porfolio.online_store.web.order;

import com.porfolio.online_store.dto.order.OrderDto;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.model.order.OrderCancelRequest;
import com.porfolio.online_store.model.order.OrderStatus;
import com.porfolio.online_store.service.cart.CheckoutService;
import com.porfolio.online_store.service.order.OrderService;
import com.porfolio.online_store.service.user.UserSessionLoaderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CheckoutService checkoutService;
    private final OrderService orderService;
    private final UserSessionLoaderService userSessionLoaderService;

    @GetMapping("/own")
    public ModelAndView getUsersOrderOwnPage(HttpSession session){
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        ModelAndView modelAndView = new ModelAndView();
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        List<OrderDto> orders = orderService.getUsersOrdersOwn(user);

        modelAndView.setViewName("orders-made");
        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @GetMapping("/recieved")
    public ModelAndView getUsersOrderRecievedPage(HttpSession session){
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        ModelAndView modelAndView = new ModelAndView();
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        List<OrderDto> orders = orderService.getUsersOrdersRecieved(user);

        modelAndView.setViewName("orders-received");
        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getOrderDetails(
            @PathVariable("id") String orderId,
            HttpSession session){
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        ModelAndView modelAndView = new ModelAndView();
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        OrderDto order = orderService.getOrderById(UUID.fromString(orderId));

        modelAndView.setViewName("order-details");
        modelAndView.addObject("user", user);
        modelAndView.addObject("order", order);
        modelAndView.addObject("cancelRequest", OrderCancelRequest.builder().build());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createOrder(
            @RequestParam("shippingAddress")String shippingAddress,
            HttpSession session){
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        ModelAndView modelAndView = new ModelAndView();
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        checkoutService.convertUsersCartToOrders(user, shippingAddress);

        modelAndView.setViewName("redirect:/orders/own");

        return modelAndView;
    }

    @PutMapping("/updatestatus/{id}/ship")
    public ModelAndView shipOrder(
            @PathVariable("id") String orderId,
            HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        orderService.shipOrder(UUID.fromString(orderId));

        modelAndView.setViewName("redirect:/orders/"+orderId);

        return modelAndView;
    }

    @PutMapping("/updatestatus/{id}/delivered")
    public ModelAndView deliverOrder(
            @PathVariable("id") String orderId,
            HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        orderService.completeOrder(UUID.fromString(orderId));

        modelAndView.setViewName("redirect:/orders/"+orderId);

        return modelAndView;
    }

    @PutMapping("/updatestatus/{id}/cancel")
    public ModelAndView updateOrderStatus(
            @ModelAttribute("cancelRequest") @Valid OrderCancelRequest cancelRequest,
            BindingResult bindingResult,
            HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("redirect:/orders/"+cancelRequest.getId());
            return modelAndView;
        }

        orderService.cancelOrder(cancelRequest);

        modelAndView.setViewName("redirect:/orders/"+cancelRequest.getId());

        return modelAndView;
    }
}
