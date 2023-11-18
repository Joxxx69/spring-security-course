
package com.spring.security.springsecuritycourse.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message);
    }
    public InvalidPasswordException(String message,Throwable cause) {
        super(message,cause);
    }
    
}