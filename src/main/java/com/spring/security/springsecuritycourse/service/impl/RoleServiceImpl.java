package com.spring.security.springsecuritycourse.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.security.springsecuritycourse.persistence.entity.security.Role;
import com.spring.security.springsecuritycourse.persistence.repository.security.RoleRepository;
import com.spring.security.springsecuritycourse.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Value("${security.default.role}")
    private String defaultRole;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findeDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
    
}
