package com.spring.security.controller;

import com.spring.security.dto.LoginRequest;
import com.spring.security.dto.RegisterDto;
import com.spring.security.dto.TokenDto;
import com.spring.security.model.TokenType;
import com.spring.security.security.JwtTokenProvider;
import com.spring.security.security.UserPrincipal;
import com.spring.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ashraf on 02-Jul-23
 * @project security
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(tokenProvider.generateToken((UserPrincipal) authentication.getPrincipal(), TokenType.ACCESS_TOKEN));
        tokenDto.setRefreshToken(tokenProvider.generateToken((UserPrincipal) authentication.getPrincipal(), TokenType.REFRESH_TOKEN));
        return ResponseEntity.ok(tokenDto);
    }

    //bura sorgu gondermek ucun user gerek login olsun
    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(tokenProvider.generateToken(userPrincipal, TokenType.ACCESS_TOKEN));
        tokenDto.setRefreshToken(tokenProvider.generateToken(userPrincipal, TokenType.REFRESH_TOKEN));
        return ResponseEntity.ok(tokenDto);
    }
   @PostMapping("/register")
    public void register(@RequestBody RegisterDto registerDto){
       authService.register(registerDto);
    }

}
