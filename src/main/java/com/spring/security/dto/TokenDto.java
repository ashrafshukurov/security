package com.spring.security.dto;

import lombok.Data;

/**
 * @author Ashraf on 03-Jul-23
 * @project security
 */

@Data
public class TokenDto {
   private String accessToken;
   private String refreshToken;


}
