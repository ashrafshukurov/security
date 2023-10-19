package com.spring.security.service;

import com.spring.security.dto.RegisterDto;
import com.spring.security.model.User;
import com.spring.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Ashraf on 03-Jul-23
 * @project security
 */

@Service
@RequiredArgsConstructor
public class DbAuthServiceImpl implements AuthService {
 private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;
   @Override
   public boolean login(String username, String password) {
      return false;
   }

   @Override
   public void register(RegisterDto registerDto) {
    User user=new User();
    user.setName(registerDto.getName());
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    userRepository.save(user);
   }
}
