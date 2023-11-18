package com.spring.security.springsecuritycourse.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.security.springsecuritycourse.dto.SaveCategoryDTO;
import com.spring.security.springsecuritycourse.persistence.entity.Category;
import com.spring.security.springsecuritycourse.persistence.repository.CategoryRepository;
import com.spring.security.springsecuritycourse.service.CategoryService;
import com.spring.security.springsecuritycourse.exception.ObjectNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService{
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOne(SaveCategoryDTO saveCategoryDTO) {
        Category category = new Category();
        category.setName(saveCategoryDTO.getName());
        category.setStatus(Category.CategoryStatus.ENABLED);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateOneById(Long categoryId, SaveCategoryDTO saveCategoryDTO) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ObjectNotFoundException("Category Not Found with id: "+ categoryId));
        categoryFromDB.setName(saveCategoryDTO.getName());

        return categoryRepository.save(categoryFromDB);
    }

    @Override
    public Category disableOneById(Long categoryId) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
            .orElseThrow(()-> new ObjectNotFoundException("Category Not Found with id: "+ categoryId));
        categoryFromDB.setStatus(Category.CategoryStatus.DISABLED);
        return categoryRepository.save(categoryFromDB);
    }



}
