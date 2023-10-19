package com.spring.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ashraf on 02-Jul-23
 * @project security
 */

@RestController
@RequestMapping("/api")
public class TestController {

   @GetMapping
   public ResponseEntity<String> getHello(){
      return ResponseEntity.ok("Hello Api");
   }

}
