package com.porfolio.online_store.service.user;

import com.porfolio.online_store.constants.ApplicationConstants;
import com.porfolio.online_store.dto.user.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.porfolio.online_store.constants.ApplicationConstants.SESSION_USER_ID;

@RequiredArgsConstructor
@Service
public class UserSessionLoaderService {

    private final UserService userService;

    public UserDto loadUserFromSession(HttpSession session){
        UUID userId = (UUID)session.getAttribute(SESSION_USER_ID);
        if(userId != null){
            return userService.getById(userId.toString());
        }
        return null;
    }
}
