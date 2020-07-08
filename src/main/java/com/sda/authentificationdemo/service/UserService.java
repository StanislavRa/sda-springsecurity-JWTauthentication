package com.sda.authentificationdemo.service;

import com.sda.authentificationdemo.model.User;

import java.util.List;

/**
 * @author StanislavR
 */
public interface UserService {

    User registerUser(User user);

    User saveUser(User user);
    List<User> findAllUsers();
    User findByUsername(String username);
    User findById(Long id);
    void deleteUser(Long id);
}
