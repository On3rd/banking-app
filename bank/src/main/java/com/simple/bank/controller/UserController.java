package com.simple.bank.controller;

import com.simple.bank.dto.UserDTO;
import com.simple.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin() {
        return "redirect:/";
    }
    @GetMapping("/register")
    public String register(@ModelAttribute("registerDTO") UserDTO userDTO) {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("userDTO") UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "redirect:/login";
    }

}
