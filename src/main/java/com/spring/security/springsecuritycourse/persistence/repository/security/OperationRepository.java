package com.spring.security.springsecuritycourse.persistence.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.security.springsecuritycourse.persistence.entity.security.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation,Long> {
    
    @Query("SELECT o FROM Operation o WHERE o.permitAll = true")
    List<Operation> findByPublicAccess();
}
