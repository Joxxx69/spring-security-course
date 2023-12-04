package com.spring.security.springsecuritycourse.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.spring.security.springsecuritycourse.dto.SaveUserDTO;
import com.spring.security.springsecuritycourse.exception.InvalidPasswordException;
import com.spring.security.springsecuritycourse.exception.ObjectNotFoundException;
import com.spring.security.springsecuritycourse.persistence.entity.Product;
import com.spring.security.springsecuritycourse.persistence.entity.security.Role;
import com.spring.security.springsecuritycourse.persistence.entity.security.User;
import com.spring.security.springsecuritycourse.persistence.repository.security.UserRepository;
import com.spring.security.springsecuritycourse.persistence.util.RoleEnumm;
import com.spring.security.springsecuritycourse.service.RoleService;
import com.spring.security.springsecuritycourse.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public User registerOneCustomer(SaveUserDTO newUser) {
        validatePassword(newUser);
        User user = new User();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        Role defaultRole = roleService.findeDefaultRole()
                .orElseThrow(()-> new ObjectNotFoundException("Role not found. Default Role"));
        user.setRole(defaultRole);

        return userRepository.save(user);

    }
    
    public void validatePassword(SaveUserDTO dto) {
        if (!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatPassword())) {
            throw new InvalidPasswordException("Passwords don't match");
        }
        if(!dto.getPassword().equals(dto.getRepeatPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        
        }
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);

    }
    
}
