package com.student.enrollment.exception;

//@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DuplicateException extends Exception {
    public DuplicateException(String message) {
        super(message);
    }
}
