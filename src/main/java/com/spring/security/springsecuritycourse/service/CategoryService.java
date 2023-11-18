package com.spring.security.springsecuritycourse.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.security.springsecuritycourse.dto.SaveCategoryDTO;
import com.spring.security.springsecuritycourse.persistence.entity.Category;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne( SaveCategoryDTO saveCategoryDTO);

    Category updateOneById(Long categoryId, SaveCategoryDTO saveCategoryDTO);

    Category disableOneById(Long categoryId);
}
