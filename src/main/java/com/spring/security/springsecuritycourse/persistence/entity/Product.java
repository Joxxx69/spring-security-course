package com.spring.security.springsecuritycourse.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long productId;

    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(
        name = "category_id",
        referencedColumnName = "categoryId"
    )
    private Category category;

    @Enumerated(EnumType.STRING)  // sirve para guardar la enumeracion en tipo String
    private ProductStatus status;

    public static enum ProductStatus {
        ENABLED, DISABLED;
    }
}
