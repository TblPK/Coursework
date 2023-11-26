package com.coursework.advice;

import com.coursework.exception.IncorrectUsernameOrPasswordException;
import com.coursework.exception.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IncorrectUsernameOrPasswordException.class)
    public String IncorrectUsernameOrPasswordException(IncorrectUsernameOrPasswordException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public String RoleNotFoundException(RoleNotFoundException ex) {
        return ex.getMessage();
    }

}
