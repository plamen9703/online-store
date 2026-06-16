package com.porfolio.online_store.service.user;

import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.dto.user.UserLoginRequest;
import com.porfolio.online_store.dto.user.UserRegisterRequest;
import com.porfolio.online_store.dto.user.UserUpdateRequest;
import com.porfolio.online_store.mapper.user.UserMapper;
import com.porfolio.online_store.model.user.User;
import com.porfolio.online_store.model.user.UserRole;
import com.porfolio.online_store.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public List<UserDto> findAll() {
        log.info("Fetching all users from the database.");
        return userRepository.findAll().stream().map(UserMapper::toUserDto).toList();
    }

    public UserDto getById(String id) {
        log.info("Fetching user with id: {}", id);
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User with this id does not exist!"));
        log.info("User found with id: {}", id);
        return UserMapper.toUserDto(user);
    }

    public UserDto register(UserRegisterRequest userRegisterRequest) {
        log.info("START: Registering new user with username: {}", userRegisterRequest.getUsername());
        userRepository.findByUsername(userRegisterRequest.getUsername()).ifPresent(user -> {
            throw new RuntimeException("User with this username already exists!");
        });
        log.info("Username {} is available. Checking email availability.", userRegisterRequest.getUsername());
        userRepository.findByEmail(userRegisterRequest.getEmail()).ifPresent(user -> {
            throw new RuntimeException("User with this email already exists!");
        });
        log.info("Email {} is available. Proceeding with user registration.", userRegisterRequest.getEmail());

        User user = UserMapper.toUserEntity(userRegisterRequest);
        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        log.info("User registered successfully with username: {}", userRegisterRequest.getUsername());
        return UserMapper.toUserDto(savedUser);

    }


    public UserDto login(UserLoginRequest userLoginRequest) {
        log.info("START: User login attempt with login: {}", userLoginRequest.getLogin());
        User user =
                userRepository.findByUsernameOrEmail(userLoginRequest.getLogin(), userLoginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("User with this username or email does not exist!"));
        log.info("User found. Verifying password for user with login");
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid user login credentials!");
        }
        log.info("User login successful with login");
        return UserMapper.toUserDto(user);
    }

    public UserDto update(String id, UserUpdateRequest userUpdateRequest) {
        log.info("START: Updating user with id: {}", id);
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User with this id does not exist!"));
        log.info("User found. Checking for updates to username and email for user.");

        if (userUpdateRequest.getFirstName() != null) {
            user.setFirstName(userUpdateRequest.getFirstName());
        }

        if (userUpdateRequest.getLastName() != null) {
            user.setLastName(userUpdateRequest.getLastName());
        }

        if (userUpdateRequest.getImageUrl() != null) {
            user.setImageUrl(userUpdateRequest.getImageUrl());
        }
        log.info("Updates verified. Saving updated user.");
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully.");
        return UserMapper.toUserDto(updatedUser);
    }

    public UserDto changeUserRole(String id, UserRole role) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User with this id does not exist!"));

        user.setRole(role);
        User updatedUser = userRepository.save(user);
        return UserMapper.toUserDto(updatedUser);
    }

}
