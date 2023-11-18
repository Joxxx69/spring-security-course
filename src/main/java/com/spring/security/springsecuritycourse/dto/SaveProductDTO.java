package com.spring.security.springsecuritycourse.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductDTO {
    
    @NotBlank
    private String name;

    @DecimalMin(value = "0.01", message = "El precio debe de ser mayor a cero")
    private BigDecimal price;

    @Min(value = 1)
    private Long categoryId;
    
}
