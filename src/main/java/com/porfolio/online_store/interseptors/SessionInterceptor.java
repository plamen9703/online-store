package com.porfolio.online_store.interseptors;

import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.model.user.UserRole;
import com.porfolio.online_store.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
import java.util.UUID;

import static com.porfolio.online_store.constants.ApplicationConstants.*;

@RequiredArgsConstructor
@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Set<String> UNAUTHENTICATED_ENDPOINTS = Set.of(
            "/",
            "/login",
            "/users/login",
            "/register",
            "/users/register",
            "/error");
    private static final Set<String> ADMIN_ENDPOINTS = Set.of("");
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestedEndpoint = request.getServletPath();
        if(UNAUTHENTICATED_ENDPOINTS.contains(requestedEndpoint)){
            return true;
        }

        HttpSession session = request.getSession();
        if(session == null){
            response.sendRedirect("/login");
            return false;
        }

        UUID userId = (UUID) session.getAttribute(SESSION_USER_ID);
        if(userId == null){
            session.invalidate();
            response.sendRedirect("/login");
            return false;
        }
        UserDto user = userService.getById(userId.toString());
        if(!user.isActive()){
            session.invalidate();
            response.sendRedirect("/login");
            return false;
        }

        if(ADMIN_ENDPOINTS.contains(requestedEndpoint) && !user.getRole().name().equals(UserRole.ADMIN.name())){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("You don't have permission to access this resourse!");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
