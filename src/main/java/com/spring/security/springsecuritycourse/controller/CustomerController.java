package com.spring.security.springsecuritycourse.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.springsecuritycourse.dto.RegisterUserDTO;
import com.spring.security.springsecuritycourse.dto.SaveUserDTO;
import com.spring.security.springsecuritycourse.persistence.entity.Product;
import com.spring.security.springsecuritycourse.persistence.entity.security.User;
import com.spring.security.springsecuritycourse.service.UserService;
import com.spring.security.springsecuritycourse.service.auth.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    //@PreAuthorize("permitAll")
    @PostMapping
    public ResponseEntity<RegisterUserDTO> registerOne(@RequestBody @Valid SaveUserDTO userNew) {
        RegisterUserDTO registeredUser = authenticationService.registerOneCustomer(userNew);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    //@PreAuthorize("denyAll")
    @GetMapping
    public ResponseEntity<Page<User>> findAll(Pageable pageable) {
        Page<User> usersPage = userService.findAll(pageable);
        if (usersPage.hasContent()) {
            return ResponseEntity.ok(usersPage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
