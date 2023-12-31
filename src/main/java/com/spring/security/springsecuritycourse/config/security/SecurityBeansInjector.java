package com.spring.security.springsecuritycourse.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.security.springsecuritycourse.exception.ObjectNotFoundException;
import com.spring.security.springsecuritycourse.persistence.repository.UserRepository;

@Configuration
public class SecurityBeansInjector {
    
    //@Autowired
    //private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder(passwordEncoder()); // sirve para ocupar los metodos encoded y match
        authenticationStrategy.setUserDetailsService(userDetailsService()); // sirve para enviar el email y enviar a la DB y asi pueda comparar con la contrasena

        return authenticationStrategy;
    }
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username)->{
            return userRepository.findByUsername(username)
            .orElseThrow(()-> new ObjectNotFoundException("User Not Found: "+ username));

        };
    }

    //@Bean
    //public UserDetailsService userDetailsService() {
    //    return new UserDetailsService() {

    //        @Override
    //        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //            return userRepository.findbyUserName(username)
    //                    .orElseThrow(() -> new ObjectNotFoundException("User Not Found: " + username));
    //        }
            
    //    };
    //}

}
