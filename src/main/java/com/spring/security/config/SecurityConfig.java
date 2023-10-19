package com.spring.security.config;

import com.spring.security.security.JwtAuthenticationFilter;
import com.spring.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Ashraf on 02-Jul-23
 * @project security
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true,
        prePostEnabled = true
)
public class SecurityConfig {
   @Autowired
   private JwtAuthenticationFilter jwtAuthenticationFilter;
   @Autowired
   private CustomUserDetailsService userDetailsService;
   @Autowired
   private final AuthenticationEntryPoint authenticationEntryPoint;

   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }
   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig) throws Exception {
      return authenticationConfig.getAuthenticationManager();
   }

   @Bean
   public SecurityFilterChain filter(HttpSecurity http) throws Exception {
      AuthenticationManagerBuilder authManagerBuilder =
              http.getSharedObject(AuthenticationManagerBuilder.class);//authendicationManager burada yaradilir
      authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());//passwordun yoxlanmasi burda olur
      var authManager = authManagerBuilder.build();
//
      http.authenticationManager(authManager);

      http.exceptionHandling()
              .authenticationEntryPoint(authenticationEntryPoint)
              .and()
              .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .httpBasic().and().csrf().disable()
              .authorizeRequests()//sorgular authorize ele
                  .antMatchers("/hello","/hello/**","/auth/login","/auth/register")//hansi pathde authorize islesin
                  .permitAll()
              .anyRequest()
              .authenticated();
         http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
         //UsernamePasswordAuthenticationFilter yeniki biz logini UserName ve password daxil etmek mentiqi ile edirk
//      sorgu servlete catmazandan evvel birinci filter ise dusur
      return http.build();
   }

}
