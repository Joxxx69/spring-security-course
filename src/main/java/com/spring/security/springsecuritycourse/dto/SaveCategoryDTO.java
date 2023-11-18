package com.spring.security.springsecuritycourse.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCategoryDTO implements Serializable {
    @NotBlank
    private String name;
}
