package com.spring.security.springsecuritycourse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ADMINISTRATOR')")
    //@PreAuthorize("hasAnyAuthority('')")
    public ResponseEntity<String> saludo() {
        return ResponseEntity.ok("hola como estas admin");
    }
    
}
