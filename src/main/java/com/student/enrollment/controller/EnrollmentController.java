package com.student.enrollment.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.EnrollmentDTO;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.entity.Enrollment;
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.EnrollmentService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/enrollment")
@CrossOrigin("*")
public class EnrollmentController {
	@Autowired
	private EnrollmentService enrollmentService;

	@Deprecated
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getEnrollmentDetails(@PathVariable("id") Long enrollmentId)
			throws ServiceException, NotFoundException {
		EnrollmentDTO enroll = enrollmentService.getEnrollmentDetails(enrollmentId);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "enrollment retrieved Successfully", enroll);
	}

	@Deprecated
	@PostMapping(value = "/staff/count", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> countStaffByDepartmentAndSubjectId(
			@RequestBody FilterOptionDTO filterOption) throws ServiceException, NotFoundException {
		Long staffCount = enrollmentService.countStaffByDepartmentAndSubjectId(filterOption);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff count retrieved Successfully",
				staffCount);
	}

	@Deprecated
	@PostMapping(value = "/student/count", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> countStudentsByDepartmentCourseAndSemesterId(
			@RequestBody FilterOptionDTO filterOption) throws ServiceException, NotFoundException {
		Long studentCount = enrollmentService.countStudentsByDepartmentCourseAndSemesterId(filterOption);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student count retrieved Successfully",
				studentCount);
	}

	@PostMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> enrollmentAvailability(@RequestBody FilterOptionDTO enrollment)
			throws ServiceException, EnrollmentException {
		boolean enroll=enrollmentService.enrollmentAvailability(enrollment);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollement is Available!!",enroll);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> enrollment(@RequestBody List<EnrollmentDTO> enrollmentDto) throws ServiceException, NotFoundException {
		List<Enrollment> enrollments = enrollmentService.enrollment(enrollmentDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollement is Success!!", enrollments);

	}

	@PostMapping(value = "/student/view", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getEnrollmentDetailsByStudent(@RequestBody EnrollmentDTO filterOption)
			throws ServiceException, NotFoundException {
		List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentDetailsByStudent(filterOption);
		if (CollectionUtils.isEmpty(enrollments)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollments Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollment retrieved Successfully!!",
				enrollments);
	}

	@PostMapping(value = "/admin/view", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getEnrollmentDetailsByAdmin(@RequestBody EnrollmentDTO filterOption)
			throws ServiceException, NotFoundException {
		List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentDetailsByAdmin(filterOption);
		if (CollectionUtils.isEmpty(enrollments)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollments Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollment retrieved Successfully!!",
				enrollments);
	}
}
