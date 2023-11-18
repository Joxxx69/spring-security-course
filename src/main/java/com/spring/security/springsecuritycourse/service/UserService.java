package com.spring.security.springsecuritycourse.service;

import com.spring.security.springsecuritycourse.dto.SaveUserDTO;
import com.spring.security.springsecuritycourse.persistence.entity.UserEntity;

public interface UserService {

    UserEntity registerOneCustomer(SaveUserDTO newUser);

    
}
