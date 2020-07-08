package com.sda.authentificationdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author StanislavR
 */
@Data
@NoArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "users")
//@Component
public class User extends BaseEntity implements Serializable {

    @Column(name = "USERNAME", nullable = false, length = 60)

    private String username;


    @Column(name = "FIRST_NAME", length = 60)
    private String firstName;


    @Column(name = "LAST_NAME", length = 60)
    private String lastName;


    @Column(name = "PASSWORD", nullable = false)
    private String password;


    @Column(name = "EMAIL", nullable = false, length = 60)
    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    public User(String username,
                String firstName,
                String lastName,
                String password,
                String email
    ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }
}
