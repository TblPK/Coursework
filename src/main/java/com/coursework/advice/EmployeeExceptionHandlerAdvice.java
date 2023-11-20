package com.coursework.advice;

import com.coursework.exception.EmployeeAlreadyExistsException;
import com.coursework.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeNotFoundException.class)
    public String EmployeeNotFoundException(EmployeeNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public String EmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex) {
        return ex.getMessage();
    }

}
