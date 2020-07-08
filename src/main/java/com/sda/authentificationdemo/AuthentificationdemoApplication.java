package com.sda.authentificationdemo;

import com.sda.authentificationdemo.model.Role;
import com.sda.authentificationdemo.model.User;
import com.sda.authentificationdemo.service.RoleService;
import com.sda.authentificationdemo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AuthentificationdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthentificationdemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserService userService, RoleService roleService) {

        return (args) -> {

            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");

            Role userRole = new Role();
            userRole.setName("ROLE_USER");

            List<Role> roles = Arrays.asList(adminRole, userRole);

            List<Role> savedRoles = roleService.saveRoles(roles);

            User adminUser = new User("Stan",
                    "Stanislav",
                    "Ratsinski",
                    "test",
                    "s.ratsinski@hotmail.com");
            User randomUser1 = new User("Margo",
                    "Margarita",
                    "Leonova",
                    "test",
                    "margo@hotmail.com");
            User randomUser2 = new User("Jonny88",
                    "John",
                    "Smith",
                    "test",
                    "jonny88@hotmail.com");

            userService.registerUser(adminUser);
            userService.registerUser(randomUser1);
            userService.registerUser(randomUser2);

            User getUserByUsername = userService.findByUsername("Stan");
            List<Role> getUserRoles = getUserByUsername.getRoles();

            Role getAdminRole = roleService.findRoleByName("ROLE_ADMIN");

            getUserRoles.add(getAdminRole);

            getUserByUsername.setRoles(getUserRoles);

            userService.saveUser(getUserByUsername);
        };
    }
}
