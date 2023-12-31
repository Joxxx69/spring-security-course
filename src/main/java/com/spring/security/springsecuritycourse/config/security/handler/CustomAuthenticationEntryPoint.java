package com.spring.security.springsecuritycourse.config.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.security.springsecuritycourse.dto.ApiErrorDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Authentication Entry Point --> Manejo de de excepciones 401 en solucitudes HTTP
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //AuthenticationEntryPoint --> devuelve texto plano

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(authException.getLocalizedMessage());
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setMessage("NO se encontraron credenciales de autenticacion." 
                                + " Por favor, inicie sesion para acceder a esta funcion");
        apiErrorDTO.setTimestamp(LocalDateTime.now());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String apirErrorAsJson = objectMapper.writeValueAsString(apiErrorDTO); // convierte un Object java en Json

        response.getWriter().write(apirErrorAsJson);
    }

    
}
