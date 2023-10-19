package com.spring.security.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Ashraf on 02-Jul-23
 * @project security
 */

@Component
@Data
public class UserPrincipal implements UserDetails {
   private Long id;

   @JsonIgnore
   private String username;
   @JsonIgnore
   private String password;
   @JsonIgnore
   private String role;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      GrantedAuthority authority=()->role;
      return List.of(authority);
   }

   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return username;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      UserPrincipal that = (UserPrincipal) o;
      return Objects.equals(id, that.id);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }
}
