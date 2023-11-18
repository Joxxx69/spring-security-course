package com.spring.security.springsecuritycourse.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.springsecuritycourse.persistence.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    
}
