package com.spring.security.springsecuritycourse.service.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.spring.security.springsecuritycourse.dto.RegisterUserDTO;
import com.spring.security.springsecuritycourse.dto.SaveUserDTO;
import com.spring.security.springsecuritycourse.dto.auth.AuthenticationRequest;
import com.spring.security.springsecuritycourse.dto.auth.AuthenticationResponse;
import com.spring.security.springsecuritycourse.exception.ObjectNotFoundException;
import com.spring.security.springsecuritycourse.persistence.entity.User;
import com.spring.security.springsecuritycourse.service.UserService;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager; // contiene al providerManager -> nos provee de authenticate()

    
    public RegisterUserDTO registerOneCustomer(SaveUserDTO newUser) {
        User user = userService.registerOneCustomer(newUser);

        RegisterUserDTO userDTO = new RegisterUserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole().name());
        userDTO.setUsername(user.getUsername());

        String jwt = jwtService.generateToken(user, generateExtraClamins(user));
        userDTO.setJwt(jwt);
        return userDTO;
    }

    public Map<String, Object> generateExtraClamins(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }
    
    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword());

        authenticationManager.authenticate(authentication); // recive un objeto de tipo Authentication para usar el Dao 

        UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClamins((User) user));

        AuthenticationResponse authResp = new AuthenticationResponse();
        authResp.setJwt(jwt);
        return authResp;
        //SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean validateToken(String jwt) {

        try {
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public User findLoggedInUser() {
        Authentication auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        //if(auth instanceof UsernamePasswordAuthenticationToken authToken){
            //  UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) auth;
            String username = (String) auth.getPrincipal();
            return userService.findOneByUsername(username)
                    .orElseThrow(()-> new ObjectNotFoundException("User not found. Username: "+username));
        //}

    }
}
