package com.controller;

import com.dto.UserLoginDto;
import com.entity.UserSession;
import com.service.UserAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
public class SignInController {
    private final UserAuthService userAuthService;
    @Autowired
    private HttpServletRequest request;

    public SignInController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/signIn")
    public String getSignInPage(@CookieValue(value = "SESSIONID", required = false) String sessionId) {
        if(sessionId != null) {
            return "redirect:home";
        }
        return "sign-in";
    }
    @PostMapping("/signIn")
    public String signIn(@ModelAttribute UserLoginDto credentialsDto, Model model, HttpServletResponse response) {
        Optional<UserSession> session = userAuthService.login(credentialsDto);
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
            return "redirect:signIn";
        }
    }
    @PostMapping("/logout")
    public String logout(@CookieValue(value = "SESSIONID") String sessionId , HttpServletResponse response) {
        userAuthService.logout(UUID.fromString(sessionId));
        Cookie cookie = new Cookie("SESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:signIn";
    }
}
