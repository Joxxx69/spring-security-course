package com.spring.security.springsecuritycourse.service.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.security.springsecuritycourse.dto.RegisterUserDTO;
import com.spring.security.springsecuritycourse.dto.SaveUserDTO;
import com.spring.security.springsecuritycourse.persistence.entity.UserEntity;
import com.spring.security.springsecuritycourse.service.UserService;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    
    public RegisterUserDTO registerOneCustomer(SaveUserDTO newUser) {
        UserEntity user = userService.registerOneCustomer(newUser);

        RegisterUserDTO userDTO = new RegisterUserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole().name());
        userDTO.setUserName(user.getUsername());

        String jwt = jwtService.generateToken(user, generateExtraClamins(user));
        userDTO.setJwt(jwt);
        return userDTO;
    }

    public Map<String, Object> generateExtraClamins(UserEntity user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole());
        extraClaims.put("authorities", user.getAuthorities());


        return extraClaims;
    }
}
