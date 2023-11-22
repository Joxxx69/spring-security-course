package com.spring.security.springsecuritycourse.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.spring.security.springsecuritycourse.dto.SaveProductDTO;
import com.spring.security.springsecuritycourse.persistence.entity.Product;


public interface ProductService {


    //a nivel de Service
    //@PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOneById(Long productId);

    Product createOne( SaveProductDTO saveProductDTO);

    Product updateOneById(Long productId, SaveProductDTO saveProductDTO);

    Product disableOneById(Long productId);
}
