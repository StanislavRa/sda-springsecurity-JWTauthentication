package com.sda.authentificationdemo.controller;

import com.sda.authentificationdemo.model.User;
import com.sda.authentificationdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * @author StanislavR
 */
@Controller
@RequestMapping(value = "/users/admin")
public class AdminController {

    UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showUser(Model model, @PathVariable Long id, Principal principal) {
        User foundUser = userService.findById(id);
        model.addAttribute("foundUser", foundUser);
        model.addAttribute("loggedInUser", principal.getName());
        model.addAttribute("classActiveAdmin", "active");
        return "user-details";
    }

    @RequestMapping(value = "/delete_user/{id}", method = RequestMethod.GET)
    public String handleDeleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
