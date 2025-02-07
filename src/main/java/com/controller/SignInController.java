package com.controller;

import com.dto.UserCredentialsDto;
import com.entity.UserSession;
import com.service.UserAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class SignInController {
    private final UserAuthService userAuthService;

    public SignInController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/signIn")
    public String getSignInPage(@CookieValue("SESSIONID") String sessionId) {
        if(sessionId != null) {
            return "index";
        }
        return "signin";
    }
    @PostMapping("/signIn")
    public String signIn(@ModelAttribute UserCredentialsDto credentials, Model model, HttpServletResponse response) {
        Optional<UserSession> session = userAuthService.login(credentials);
        if (session.isPresent()) {
            Cookie sessionCookie = new Cookie("SESSIONID", session.get().getId().toString());
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            sessionCookie.setMaxAge(86400);
            response.addCookie(sessionCookie);
            model.addAttribute("sessionId", session.get().getId());
            return "index";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "sign-in";
        }
    }
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        return null;
    }
}
