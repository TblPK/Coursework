package com.coursework.advice;

import com.coursework.exception.InvalidShiftTimeException;
import com.coursework.exception.ScheduleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScheduleExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidShiftTimeException.class)
    public String InvalidShiftTimeException(InvalidShiftTimeException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ScheduleNotFoundException.class)
    public String ScheduleNotFoundException(ScheduleNotFoundException ex) {
        return ex.getMessage();
    }

}
