package com.simple.bank.controller;

import com.simple.bank.config.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserController {
    @ModelAttribute("currentUser")
    public CustomUserDetails getCurrentUser(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return customUserDetails;
    }
}
