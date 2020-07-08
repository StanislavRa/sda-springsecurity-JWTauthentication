package com.sda.authentificationdemo.controller;

import com.sda.authentificationdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author StanislavR
 */
@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("classActiveUsers", "active");
        return "users";
    }
}
