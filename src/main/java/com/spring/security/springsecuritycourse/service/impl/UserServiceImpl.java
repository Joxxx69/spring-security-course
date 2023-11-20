package com.spring.security.springsecuritycourse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.spring.security.springsecuritycourse.dto.SaveUserDTO;
import com.spring.security.springsecuritycourse.exception.InvalidPasswordException;
import com.spring.security.springsecuritycourse.persistence.entity.UserEntity;
import com.spring.security.springsecuritycourse.persistence.repository.UserRepository;
import com.spring.security.springsecuritycourse.persistence.util.Role;
import com.spring.security.springsecuritycourse.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity registerOneCustomer(SaveUserDTO newUser) {
        UserEntity user = new UserEntity();

        validatePassword(newUser);
        
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setName(newUser.getName());
        user.setUserName(newUser.getUserName());
        user.setRole(Role.ROLE_CUSTOMER);

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
    
}
