package com.spring.security.springsecuritycourse.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO implements Serializable {
    
    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt;

}
