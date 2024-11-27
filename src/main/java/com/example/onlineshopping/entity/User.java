package com.example.onlineshopping.entity;

import com.example.onlineshopping.contants.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
@Setter
@Getter
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean verified;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private Role role;
    private String address;
    private String city;
    private String state;
}
