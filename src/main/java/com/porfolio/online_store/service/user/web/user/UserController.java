package com.porfolio.online_store.service.user.web.user;

import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.dto.user.UserLoginRequest;
import com.porfolio.online_store.dto.user.UserRegisterRequest;
import com.porfolio.online_store.dto.user.UserUpdateRequest;
import com.porfolio.online_store.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String login(@ModelAttribute("loginRequestData") @Valid UserLoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        UserDto loggedIn = null;
        try {
            loggedIn = userService.login(loginRequest);
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("loginRequestData", "Invalid username or password"));
            return "login";
        }
        return "redirect:/users/profile/" + loggedIn.getId();
    }

    @GetMapping("/profile/{id}")
    public ModelAndView getProfile(@PathVariable("id") String id) {
            UserDto userDto = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", userDto);
        return modelAndView;
    }

    @GetMapping("/profile/{id}/edit")
    public ModelAndView getEditProfile(@PathVariable("id") String id) {
        UserDto userDto = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile-edit");
        modelAndView.addObject("userUpdateRequest", userDto);
        return modelAndView;
    }

    @PutMapping("/profile/{id}/edit")
    public ModelAndView editProfile(@PathVariable("id") String id, @ModelAttribute("userUpdateRequest") @Valid UserUpdateRequest userUpdateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("profile-edit");
            modelAndView.addObject("userUpdateRequest", userUpdateRequest);
            return modelAndView;
        }
        UserDto updatedUser = userService.update(id, userUpdateRequest);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users/profile/" + updatedUser.getId());
        return modelAndView;
    }

}
