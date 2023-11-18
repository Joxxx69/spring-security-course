package com.spring.security.springsecuritycourse.exception;

import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.security.springsecuritycourse.dto.ApiErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(HttpServletRequest request, Exception exception) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(exception.getLocalizedMessage());
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setMessage("Error interno en el servidor, vuelva a intentarlo");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorDTO);
        
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(HttpServletRequest request,
                                                                    MethodArgumentNotValidException exception) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(exception.getLocalizedMessage());
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setTimestamp(LocalDateTime.now());
        apiErrorDTO.setMessage("Error en la peticion enviada");

        //Map<String, Object> errorList = new HashMap<>();

        //exception.getBindingResult().getFieldErrors().forEach((error) -> {
        //    errorList.put(error.getField(), error.getDefaultMessage());
        //});

        System.out.println(exception.getAllErrors().stream().map(error-> error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorDTO);
        
    }
    
}
