package com.student.enrollment.controller;

import static java.util.Optional.ofNullable;

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
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.EnrollmentService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin("*")
public class EnrollmentController {

	@Autowired
	private EnrollmentService enrollmentService;

	/**
	 * To Get Enrollment Details By Id
	 * 
	 * @param enrollmentId
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@Deprecated
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getEnrollmentDetails(@PathVariable("id") Long enrollmentId)
			throws ServiceException, NotFoundException {
		return ofNullable(enrollmentService.getEnrollmentDetails(enrollmentId))

				.map(enroll -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Enrollment retrieved Successfully", enroll))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollment Not Found"));

	}

	/**
	 * To find Enrollment Availability
	 * 
	 * @param filterOption
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws EnrollmentException
	 */
	@PostMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> enrollmentAvailability(@RequestBody FilterOptionDTO filterOption)
			throws ServiceException, EnrollmentException {
		return ofNullable(enrollmentService.isEnrollmentAvailable(filterOption))
				.map(isEnrollmentAvailable -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Enrollement is Available!!", isEnrollmentAvailable))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollment Not Found"));

	}

	/**
	 * To save Enrollment
	 * 
	 * @param enrollmentDto
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws EnrollmentException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> saveEnrollment(@RequestBody List<EnrollmentDTO> enrollmentDtos)
			throws ServiceException, NotFoundException, EnrollmentException {
		enrollmentService.saveEnrollments(enrollmentDtos);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollement is Saved Successfully!!");
	}

	/**
	 * To get Enrollment Details For Student
	 * 
	 * @param enrollmentDto
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PostMapping(value = "/view/student", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getEnrollmentDetailsForStudentView(
			@RequestBody EnrollmentDTO enrollmentDto) throws ServiceException, NotFoundException {
		return ofNullable(enrollmentService.getEnrollmentDetailsForStudentView(enrollmentDto))
				.filter(CollectionUtils::isNotEmpty)
				.map(enrollments -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Enrollment details are retrieved Successfully!!", enrollments))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollments Not Found",
						new ArrayList<>()));
	}

	/**
	 * To get Enrollment Details For Admin
	 * 
	 * @param filterOption
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PostMapping(value = "/view/admin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getEnrollmentDetailsForAdminView(@RequestBody EnrollmentDTO filterOption)
			throws ServiceException, NotFoundException {
		return ofNullable(enrollmentService.getEnrollmentDetailsForAdminView(filterOption))
				.filter(CollectionUtils::isNotEmpty)
				.map(enrollments -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Enrollment details are retrieved Successfully!!", enrollments))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollments Not Found",
						new ArrayList<>()));
	}
}
