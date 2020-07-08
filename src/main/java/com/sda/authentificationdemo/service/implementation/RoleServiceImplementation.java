package com.sda.authentificationdemo.service.implementation;

import com.sda.authentificationdemo.model.Role;
import com.sda.authentificationdemo.model.Status;
import com.sda.authentificationdemo.model.User;
import com.sda.authentificationdemo.repository.RoleRepository;
import com.sda.authentificationdemo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author StanislavR
 */
@Service("roleService")
@Slf4j
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;


    @Autowired
    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveRole(Role role) {

        Role savedRole = roleRepository.save(role);
        log.info("IN saveRole - role: {} successfully saved", savedRole);
        return savedRole;
    }

    @Override
    public List<Role> saveRoles(List<Role> roles) {

        List<Role> savedRoles = roleRepository.saveAll(roles);
        log.info("IN saveRoles - roles: {} successfully saved", savedRoles);

        return savedRoles;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
