package com.spring.security.springsecuritycourse.service;

import java.util.Optional;

import com.spring.security.springsecuritycourse.persistence.entity.security.Role;

public interface RoleService {
    
    Optional<Role> findeDefaultRole();
}
