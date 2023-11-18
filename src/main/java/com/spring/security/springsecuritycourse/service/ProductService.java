package com.spring.security.springsecuritycourse.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.security.springsecuritycourse.dto.SaveProductDTO;
import com.spring.security.springsecuritycourse.persistence.entity.Product;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOneById(Long productId);

    Product createOne( SaveProductDTO saveProductDTO);

    Product updateOneById(Long productId, SaveProductDTO saveProductDTO);

    Product disableOneById(Long productId);
}
