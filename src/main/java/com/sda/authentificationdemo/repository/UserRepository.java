package com.sda.authentificationdemo.repository;

import com.sda.authentificationdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author StanislavR
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);

}
