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
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.EnrollmentScheduleDTO;
import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.EnrollmentScheduleService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/enrollments/schedule")
@CrossOrigin("*")
public class EnrollmentScheduleController {

	@Autowired
	private EnrollmentScheduleService enrollmentScheduleService;

	/**
	 * To Schedule Enrollments
	 * 
	 * @param enrollmentScheduleDto
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws DuplicateException
	 * @throws NotFoundException
	 * @throws EnrollmentException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> schdeduleEnrollment(
			@RequestBody @Valid EnrollmentScheduleDTO enrollmentScheduleDto)
			throws ServiceException, DuplicateException, NotFoundException, EnrollmentException {
		enrollmentScheduleService.schdeduleEnrollment(enrollmentScheduleDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollment Scheduled Successfully!!");

	}

}
