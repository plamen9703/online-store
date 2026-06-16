package com.porfolio.online_store.web;

import com.porfolio.online_store.constants.ApplicationConstants;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.dto.user.UserLoginRequest;
import com.porfolio.online_store.dto.user.UserRegisterRequest;
import com.porfolio.online_store.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {

    private final UserService userService;

    @GetMapping
    public ModelAndView getIndex(HttpSession session){
        UUID userId = (UUID) session.getAttribute(ApplicationConstants.SESSION_USER_ID);
        UserDto user = null;
        if(userId !=null ){
            user = userService.getById(userId.toString());
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("login")
    public ModelAndView getLogin(HttpSession session) {
        UUID userId = (UUID) session.getAttribute(ApplicationConstants.SESSION_USER_ID);
        ModelAndView modelAndView = new ModelAndView();
        if(userId !=null ){
            modelAndView.setViewName("redirect:/users/profile");
            return modelAndView;
        }

        modelAndView.setViewName("login");
        modelAndView.addObject("loginRequestData", new UserLoginRequest());
        modelAndView.addObject("user", null);
        return modelAndView;
    }

    @GetMapping("register")
    public ModelAndView getRegister(HttpSession session) {
        UUID userId = (UUID) session.getAttribute(ApplicationConstants.SESSION_USER_ID);
        ModelAndView modelAndView = new ModelAndView();
        if(userId !=null ){
            modelAndView.setViewName("redirect:/users/profile");
            return modelAndView;
        }
        modelAndView.setViewName("register");
        modelAndView.addObject("registerRequestData", new UserRegisterRequest());
        modelAndView.addObject("user", null);
        return modelAndView;
    }

}
