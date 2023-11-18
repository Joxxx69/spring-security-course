package com.spring.security.springsecuritycourse.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDTO implements Serializable{
    
    @Size(min = 4)
    private String name;

    private String userName;

    @Size(min = 8)
    private String password;

    @Size(min = 8)
    private String repeatPassword;
}
