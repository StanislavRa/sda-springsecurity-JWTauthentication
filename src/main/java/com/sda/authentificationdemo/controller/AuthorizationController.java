package com.sda.authentificationdemo.controller;

import com.sda.authentificationdemo.model.User;
import com.sda.authentificationdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author StanislavR
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthorizationController {

    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("classActiveRegister", "active");
        return "/register";
    }

    @PostMapping("/register")
    public String submitForm(Model model, @ModelAttribute("user") User user) {
        userService.registerUser(user);
        model.addAttribute("classActiveRegister", "active");

        return "register-success";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("classActiveLogin", "active");
        return "login";
    }

    @GetMapping("/changePassword")
    public String showChangePassword(Model model) {
        model.addAttribute("classActiveChangePass", "active");
        return "change-password";
    }
}
