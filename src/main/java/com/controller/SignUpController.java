package com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signUp")
@Slf4j
public class SignUpController {
    @GetMapping
    public String getSignUpPage() {
        log.info("SignUp");
        //если еще нет сессии
        return "sign-up";
    }
}
