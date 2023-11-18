package com.spring.security.springsecuritycourse.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDTO implements Serializable{
    
    private String backendMessage;
    private String message;
    private String url;
    private String method;
    private LocalDateTime timestamp;



}
