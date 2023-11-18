package com.spring.security.springsecuritycourse.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.springsecuritycourse.dto.SaveCategoryDTO;
import com.spring.security.springsecuritycourse.persistence.entity.Category;
import com.spring.security.springsecuritycourse.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAll(pageable);
        if (categoryPage.hasContent()) {
            return ResponseEntity.ok(categoryPage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //return ResponseEntity.notFound().build();
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findOneById(@PathVariable Long categoryId) {
        Optional<Category> category = categoryService.findOneById(categoryId);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping
    public ResponseEntity<Category> createOne(@RequestBody @Valid SaveCategoryDTO SaveCategoryDTO) {
        Category category = categoryService.createOne(SaveCategoryDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateOneById(@PathVariable Long categoryId,
            @RequestBody @Valid SaveCategoryDTO saveCategoryDTO) {
        Category category = categoryService.updateOneById(categoryId, saveCategoryDTO);

        return ResponseEntity.ok(category);
    }
    
    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<Category> disableOneById(@PathVariable Long categoryId) {
        Category category = categoryService.disableOneById(categoryId);
        return ResponseEntity.ok(category);
    }
}
