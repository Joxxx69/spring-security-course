package com.spring.security.springsecuritycourse.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//configura por default los elementos de se seguridad(authenticationConfiguration etc)
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;
    
    //HttpSecurity -> permite personalizar como se gestionan y protegen las peticiones http
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        SecurityFilterChain filterChain = http
            .csrf(csrfConfig -> csrfConfig.disable()) // desactiva el uso de tokens ya que usaremos Jwt
            .sessionManagement(sessMagConfig->sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // sesion sin estado
            .authenticationProvider(daoAuthProvider)
            .authorizeHttpRequests(authReqConfig->{  //configuracion de rutas publicas
                    authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
                    //authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate");
                    //authReqConfig.requestMatchers(HttpMethod.POST,"/auth/validate");
                    authReqConfig.requestMatchers(HttpMethod.POST, "/auth/**");
                    
                    authReqConfig.anyRequest().authenticated();
             })
                .build();
            
        return filterChain;

    }
    
}