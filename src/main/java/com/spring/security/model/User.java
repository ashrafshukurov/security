package com.spring.security.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Ashraf on 03-Jul-23
 * @project security
 */

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String role;
}
