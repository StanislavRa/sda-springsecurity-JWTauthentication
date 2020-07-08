package com.sda.authentificationdemo.service.implementation;

/**
 * @author StanislavR
 */

import com.sda.authentificationdemo.model.Role;
import com.sda.authentificationdemo.model.Status;
import com.sda.authentificationdemo.model.User;
import com.sda.authentificationdemo.repository.RoleRepository;
import com.sda.authentificationdemo.repository.UserRepository;
import com.sda.authentificationdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImplementation implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User registerUser(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User savedUser = userRepository.save(user);
        log.info("IN registerUser - user: {} successfully saved", savedUser);

        return savedUser;
    }

    @Override
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        log.info("IN save - user: {} successfully saved", savedUser);

        return savedUser;
    }


    @Override
    public List<User> findAllUsers() {
        List<User> result = userRepository.findAll();
        log.info("IN findAllUsers - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }


    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN findByUsername - user: {} found by id: {}", result, id);
        return result;
    }


    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);

    }
}
