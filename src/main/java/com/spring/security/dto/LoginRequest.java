package com.spring.security.dto;

import lombok.Data;

/**
 * @author Ashraf on 02-Jul-23
 * @project security
 */

@Data
public class LoginRequest {
   private String username;
   private String password;
}
