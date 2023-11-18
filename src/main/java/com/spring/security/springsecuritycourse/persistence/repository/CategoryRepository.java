package com.spring.security.springsecuritycourse.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.springsecuritycourse.persistence.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
    
}
