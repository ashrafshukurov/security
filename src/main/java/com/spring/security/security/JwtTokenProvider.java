package com.spring.security.security;

import com.spring.security.model.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Ashraf on 02-Jul-23
 * @project security
 */

@Component
@Slf4j
public class JwtTokenProvider {
   @Value("${security.jwtSecret}")
   private String jwtSecret;
   @Value("${security.accessTokenExpirationInMs}")
   private int accessTokenExpirationInMs;

   @Value("${security.refreshTokenExpirationInMs}")
   private int refreshTokenExpirationInMs;

   public String generateToken(UserPrincipal userPrincipal,TokenType tokenType) {
      var expirationInMs=tokenType== TokenType.ACCESS_TOKEN ? accessTokenExpirationInMs :refreshTokenExpirationInMs;
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + accessTokenExpirationInMs);
      return Jwts.builder()
              .setSubject(Long.toString(userPrincipal.getId()))
              .setIssuedAt(new Date())
              .setExpiration(expiryDate)
              .signWith(SignatureAlgorithm.HS512, jwtSecret)
              .compact();
   }
   public Long getUserIdFromJWT(String token) {
      Claims claims = Jwts.parser()
              .setSigningKey(jwtSecret)
              .parseClaimsJws(token)
              .getBody();//payload
      return Long.parseLong(claims.getSubject());
   }
   public boolean validateToken(String authToken) {
      try {
         Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);//burda jwt neye parse olunur
         return true;
      } catch (SignatureException ex) {
         log.error("Invalid JWT signature");
      } catch (MalformedJwtException ex) {
         log.error("Invalid JWT token");
      } catch (ExpiredJwtException ex) {
         log.error("Expired JWT token");
      } catch (UnsupportedJwtException ex) {
         log.error("Unsupported JWT token");
      } catch (IllegalArgumentException ex) {
         log.error("JWT claims string is empty.");
      }
      return false;
   }
}
