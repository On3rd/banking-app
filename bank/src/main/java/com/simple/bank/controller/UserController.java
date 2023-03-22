package com.simple.bank.controller;

import com.simple.bank.dto.UserDTO;
import com.simple.bank.service.UserService;
import com.simple.bank.validator.UserCreateFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserCreateFormValidator userCreateFormValidator;
    @InitBinder("userDTO")
    public void registerFormInitBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin() {
        return "redirect:/";
    }
    @GetMapping("/register")
    public String register(@ModelAttribute("userDTO") UserDTO userDTO) {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("userDTO") UserDTO userDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "register";
        }
        userService.registerUser(userDTO);
        return "redirect:/login";
    }

}
