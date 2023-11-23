package com.spring.security.springsecuritycourse.config.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.security.springsecuritycourse.dto.ApiErrorDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(accessDeniedException.getLocalizedMessage());
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setMessage("Acceso Denegado. NO tienes los permisos necesarios para acceder a esta funcion." 
                                + " Por favor, contacta al administrador si crees que esto es un error");
        apiErrorDTO.setTimestamp(LocalDateTime.now());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String apirErrorAsJson = objectMapper.writeValueAsString(apiErrorDTO); // convierte un Object java en Json

        response.getWriter().write(apirErrorAsJson);
    }
    
}
