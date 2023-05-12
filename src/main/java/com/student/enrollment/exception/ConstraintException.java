package com.student.enrollment.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ConstraintException extends Exception {
    public ConstraintException(String message) {
        super(message);
    }

}
