package com.porfolio.online_store.web.user;

import com.porfolio.online_store.constants.ApplicationConstants;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.dto.user.UserLoginRequest;
import com.porfolio.online_store.dto.user.UserRegisterRequest;
import com.porfolio.online_store.dto.user.UserUpdateRequest;
import com.porfolio.online_store.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import static com.porfolio.online_store.constants.ApplicationConstants.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequestData") @Valid UserRegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        UserDto registered = userService.register(registerRequest);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequestData") @Valid UserLoginRequest loginRequest,
                        BindingResult bindingResult,
                        HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        UserDto loggedIn = null;
        try {
            loggedIn = userService.login(loginRequest);
            session.setAttribute(SESSION_USER_ID, loggedIn.getId());
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("loginRequestData", "Invalid username or password"));
            return "login";
        }
        return "redirect:/users/profile";
    }

    @GetMapping("/profile")
    public ModelAndView getProfile(HttpSession session) {
        UUID userId = (UUID)session.getAttribute(SESSION_USER_ID);
        UserDto userDto = userService.getById(userId.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", userDto);
        return modelAndView;
    }

    @GetMapping("/profile/edit")
    public ModelAndView getEditProfile(HttpSession session) {
        UUID userId = (UUID) session.getAttribute(SESSION_USER_ID);
        UserDto userDto = userService.getById(userId.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile-edit");
        modelAndView.addObject("userUpdateRequest", userDto);
        modelAndView.addObject("user", userDto);
        return modelAndView;
    }

    @PutMapping("/profile/edit")
    public ModelAndView editProfile(@ModelAttribute("userUpdateRequest") @Valid UserUpdateRequest userUpdateRequest,
                                    BindingResult bindingResult,
                                    HttpSession session) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("profile-edit");
            modelAndView.addObject("userUpdateRequest", userUpdateRequest);
            return modelAndView;
        }
        UUID userId = (UUID) session.getAttribute(SESSION_USER_ID);
        UserDto updatedUser = userService.update(userId.toString(), userUpdateRequest);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users/profile");
        return modelAndView;
    }


    @GetMapping("/logout")
    public ModelAndView getLogoutPage(HttpSession session){
        session.invalidate();
        return new ModelAndView("redirect:/");
    }
}
