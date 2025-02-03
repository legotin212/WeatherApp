package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/SignUp")
public class SignUpController {
    @GetMapping
    public String getSignUpPage() {
        //если еще нет сессии
        return "sign-up.html";
    }
}
