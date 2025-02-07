package com.controller;

import com.dto.UserCredentialsDto;
import com.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignUpController {
    private final UserAuthService userAuthService;
    @Autowired
    public SignUpController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "sign-up";
    }

    @PostMapping("/signUp")
    public String singUp(@RequestBody UserCredentialsDto credentials) {
        //валидация имени

        //валидация паролей

        userAuthService.createUser(credentials);

        return "sign-in";
    }
}
