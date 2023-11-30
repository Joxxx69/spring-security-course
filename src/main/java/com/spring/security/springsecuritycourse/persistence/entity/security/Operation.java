package com.spring.security.springsecuritycourse.persistence.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Operation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;

    private String name; // READ_ALL_PRODUCTS,.....

    private String path; // --> /products/{productId}/disable --> aqui en path solo va a estar el path base = /products | /categories | ...

    private String httpMethod;

    private boolean permitAll;

    @ManyToOne
    @JoinColumn(
        name = "module_id",
        referencedColumnName = "moduleId"
    )
    private Module module;
}
