package com.spring.security.springsecuritycourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.springsecuritycourse.dto.RegisterUserDTO;
import com.spring.security.springsecuritycourse.dto.SaveUserDTO;
import com.spring.security.springsecuritycourse.service.auth.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AuthenticationService authenticationService;
    

    @PostMapping
    public ResponseEntity<RegisterUserDTO> registerOne(@RequestBody @Valid SaveUserDTO userNew) {
        RegisterUserDTO registeredUser = authenticationService.registerOneCustomer(userNew);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}
