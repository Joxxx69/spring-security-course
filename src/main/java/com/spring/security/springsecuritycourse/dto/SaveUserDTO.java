package com.spring.security.springsecuritycourse.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDTO implements Serializable{
    
    @Size(min = 4)
    private String name;

    @NotBlank
    @NonNull
    private String username;

    @Size(min = 8)
    private String password;

    @Size(min = 8)
    private String repeatPassword;
}
