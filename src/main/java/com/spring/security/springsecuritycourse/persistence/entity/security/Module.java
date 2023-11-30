package com.spring.security.springsecuritycourse.persistence.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Module {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long moduleId;

    private String name; // PRODUCT, CATEGORY, AUTH, CUSTOMER

    private String basePath; //--> /products
}
