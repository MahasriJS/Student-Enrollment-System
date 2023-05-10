package com.student.enrollment.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.dto.LoginDTO;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.LoginService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {
	@Autowired
	private LoginService loginService;

	/**
	 * To Login
	 * 
	 * @param loginDto
	 * @param userType
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> login(@RequestBody @Valid LoginDTO loginDto,
			@RequestParam(defaultValue = "Student", required = false) String userType)
			throws ServiceException, NotFoundException {
		Long id = loginService.login(loginDto, userType);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Logged in Successfully!!", id);

	}
}
