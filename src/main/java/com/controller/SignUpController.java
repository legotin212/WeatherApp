package com.controller;

import com.dto.UserLoginDto;
import com.dto.SignUpUserDto;
import com.exception.UserAlreadyExistsException;
import com.service.UserAuthService;
import com.util.validator.SignUpUserDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    private final UserAuthService userAuthService;
    private final SignUpUserDTOValidator userCredentialsValidator;
    @Autowired
    public SignUpController(UserAuthService userAuthService, SignUpUserDTOValidator userCredentialsValidator) {
        this.userAuthService = userAuthService;
        this.userCredentialsValidator = userCredentialsValidator;
    }

    @GetMapping("/signUp")
    public String getSignUpPage(@ModelAttribute("userRegistrationDto") SignUpUserDto userRegistrationDto, Model model) {
        model.addAttribute("signUpUserDto", SignUpUserDto.builder().build());
        return "sign-up";
    }

    @PostMapping("/signUp")
    public String singUp(@ModelAttribute("signUpUserDto") @Validated SignUpUserDto credentials, BindingResult bindingResult, Model model) {
        userCredentialsValidator.validate(credentials,bindingResult);
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        try {
            userAuthService.createUser(new UserLoginDto(credentials.getUsername(),credentials.getPassword()));
        }
        catch (UserAlreadyExistsException exception){
            bindingResult.rejectValue("username", "error.username", "Username already exists");
            return "sign-up";
        }
        return "sign-in";
    }
}
