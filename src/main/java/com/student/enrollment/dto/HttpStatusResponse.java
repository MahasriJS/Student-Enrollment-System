package com.student.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpStatusResponse {
	private Integer statusCode;
	private Object data;
	private String message;
	

	public HttpStatusResponse(Integer statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public HttpStatusResponse(Integer statusCode, Object data, String message) {
		super();
		this.statusCode = statusCode;
		this.data = data;
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
