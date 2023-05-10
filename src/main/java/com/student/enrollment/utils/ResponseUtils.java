package com.student.enrollment.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.student.enrollment.dto.HttpStatusResponse;

public class ResponseUtils {
	/**
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */

	public static ResponseEntity<HttpStatusResponse> getSuccessResponse(Integer code, String message, Object data) {
		return new ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(code, data, message), HttpStatus.OK);
	}

	/**
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static ResponseEntity<HttpStatusResponse> httpStatusException(Integer code, String message) {
		return new ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(code, message),
				HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static ResponseEntity<HttpStatusResponse> getSuccessResponse(Integer code, String message) {
		return new ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(code, message), HttpStatus.OK);
	}

}
