package com.spring.security.springsecuritycourse.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObjectNotFoundException extends RuntimeException {
        
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
