package com.sda.authentificationdemo.repository;

import com.sda.authentificationdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author StanislavR
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
