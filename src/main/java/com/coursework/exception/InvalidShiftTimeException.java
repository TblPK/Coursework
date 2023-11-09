package com.coursework.exception;

public class InvalidShiftTimeException extends RuntimeException {
    public InvalidShiftTimeException(String message) {
        super(message);
    }
}
