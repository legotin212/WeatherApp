package com.interceptor;

import com.exception.NonAuthorizedException;
import com.repository.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final SessionRepository sessionRepository;
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/signUp", "/signIn", "/logout",
            "/css/", "/js/", "/images/"
    );
    @Autowired
    public AuthInterceptor(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (PUBLIC_ENDPOINTS.stream().anyMatch(requestURI::startsWith)) {
            return true;
        }
        String sessionId = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> "SESSIONID".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        if (sessionId != null && sessionRepository.findById(UUID.fromString(sessionId)).isPresent()) {
            return true;
        } else {
            throw new NonAuthorizedException("Not authorized");
        }
    }
}
