package com.spring.security.service;

import com.spring.security.dto.RegisterDto;

/**
 * @author Ashraf on 03-Jul-23
 * @project security
 */

public interface AuthService {
   boolean login(String username,String password);
   void register(RegisterDto registerDto);
}
