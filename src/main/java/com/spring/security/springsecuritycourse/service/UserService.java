package com.spring.security.springsecuritycourse.service;

import java.util.Optional;

import com.spring.security.springsecuritycourse.dto.SaveUserDTO;
import com.spring.security.springsecuritycourse.persistence.entity.User;

public interface UserService {

    User registerOneCustomer(SaveUserDTO newUser);

    Optional<User> findOneByUsername(String username);

}
