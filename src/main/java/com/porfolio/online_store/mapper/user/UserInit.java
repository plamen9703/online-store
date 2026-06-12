package com.porfolio.online_store.mapper.user;

import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.dto.user.UserRegisterRequest;
import com.porfolio.online_store.model.user.UserRole;
import com.porfolio.online_store.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.porfolio.online_store.constants.ApplicationConstants.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserInit implements CommandLineRunner {

    private final UserService userService;


    @Override
    public void run(String... args) throws Exception {
        List<UserDto> adminUsers = userService.findAll()
                .stream()
                .filter(user -> user.getRole() == UserRole.ADMIN).toList();
        if(!adminUsers.isEmpty()){
            return;
        }
        String userEnv = System.getenv(DEFAULT_USER_USERNAME);
        String emailEnv = System.getenv(DEFAULT_USER_EMAIL);
        String passwordEnv = System.getenv(DEFAULT_USER_PASSWORD);
        log.info("No admin user found. Creating default admin user with username: {}", userEnv);
        UserRegisterRequest adminUserRegisterRequest = UserRegisterRequest.builder()
                .username(userEnv)
                .email(emailEnv)
                .password(passwordEnv)
                .firstName("")
                .lastName("")
                .build();

        UserDto registeredUser = userService.register(adminUserRegisterRequest);

        userService.changeUserRole(registeredUser.getId().toString(), UserRole.ADMIN);

        log.info("Default admin user with username: {} created successfully!", userEnv);
    }
}
