package com.sda.authentificationdemo.service;

import com.sda.authentificationdemo.model.Role;

import java.util.List;

/**
 * @author StanislavR
 */
public interface RoleService {
    Role saveRole(Role role);

    List<Role> saveRoles(List<Role> roles);

    Role findRoleByName(String name);

}
