package com.student.enrollment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.utils.ResponseUtils;

@ControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<HttpStatusResponse> handleServiceException(ServiceException ex) {
		return ResponseUtils.httpStatusException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed To Process Request");
	}

	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<HttpStatusResponse> handleDuplicateEmailException(DuplicateException ex) {
		return ResponseUtils.httpStatusException(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<HttpStatusResponse> handleNotFoundException(NotFoundException ex) {
		return ResponseUtils.httpStatusException(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
	}

	@ExceptionHandler(EnrollmentException.class)
	public ResponseEntity<HttpStatusResponse> handleEnrollmentException(EnrollmentException ex) {
		return ResponseUtils.httpStatusException(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
	}
	
	@ExceptionHandler(InvalidPhoneNumberException.class)
	public ResponseEntity<HttpStatusResponse> handleNotFoundException(InvalidPhoneNumberException ex) {
		return ResponseUtils.httpStatusException(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpStatusResponse> handleValidationException(MethodArgumentNotValidException ex) {
		if(ex.getMessage().contains("email")) {
			return ResponseUtils.httpStatusException(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Invalid Email");
		}
		if(ex.getMessage().contains("phno")) {
        return ResponseUtils.httpStatusException(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Phone number should be 10-12 digits");
		}
		return ResponseUtils.httpStatusException(HttpStatus.UNPROCESSABLE_ENTITY.value(),ex.getMessage());
    }
}
