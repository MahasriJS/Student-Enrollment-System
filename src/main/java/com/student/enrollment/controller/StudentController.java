package com.student.enrollment.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.ChangePaswordDTO;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ConstraintException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.StudentService;
import com.student.enrollment.utils.ResponseUtils;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import javax.validation.Valid;

@RestController
@RequestMapping("/students")
@CrossOrigin("*")
public class StudentController {

	@Autowired
	private StudentService studentService;

	/**
	 * To Add Student
	 * 
	 * @param student
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws DuplicateException
	 * @throws NotFoundException
	 * @throws ConstraintException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> addStudent(@RequestBody @Valid StudentDTO student)
			throws ServiceException, DuplicateException, NotFoundException, ConstraintException {
		studentService.addStudent(student);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student Added Successfully!!");
	}

	/**
	 * To Get Staff By Id
	 * 
	 * @param studentId
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getStudentById(@PathVariable("id") Long studentId)
			throws ServiceException, NotFoundException {
		return ofNullable(studentService.getStudentById(studentId))
				.map(student -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Student retrieved Successfully", student))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student Not Found",
						new ArrayList<>()));
	}

	/**
	 * To Update Student Status
	 * 
	 * @param id
	 * @param isAvailable
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/{id}")
	public ResponseEntity<HttpStatusResponse> updateStudentStatus(@PathVariable("id") Long id,
			@RequestParam(defaultValue = "true", required = false) Boolean isAvailable)
			throws ServiceException, NotFoundException {
		studentService.updateAvailability(id, isAvailable);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student Status Updated Successfully");
	}

	/**
	 * To Update Student By Id
	 * 
	 * @param id
	 * @param student
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws DuplicateException
	 * @throws ConstraintException
	 */
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> updateStudentById(@PathVariable @Valid Long id,
			@RequestBody StudentDTO student)
			throws ServiceException, NotFoundException, DuplicateException, ConstraintException {
		studentService.updateStudentById(id, student);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student updated Successfully");
	}

	/**
	 * To Get Student By Department and Course and Semester Id
	 * 
	 * @param filterOption
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PostMapping(value = "/ids", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getStudentsByDeptAndCourseAndSemId(
			@RequestBody FilterOptionDTO filterOption) throws ServiceException, NotFoundException {
		return ofNullable(studentService.getStudentsByDeptAndCourseAndAcademicYear(filterOption))
				.filter(CollectionUtils::isNotEmpty)
				.map(students -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Students retrieved Successfully!!", students))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Students Not Found",
						new ArrayList<>()));

	}

	/**
	 * To Change Password
	 * 
	 * @param changePaswordDto
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PostMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> changePassword(@RequestBody ChangePaswordDTO changePaswordDto)
			throws ServiceException, NotFoundException {
		studentService.changePassword(changePaswordDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Password Successfully Changed!!");

	}

	/**
	 * To Upgrade Student
	 * 
	 * @param filterOption
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	@PostMapping(value = "/upgrade", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> upgradeStudent(@RequestBody FilterOptionDTO filterOption)
			throws NotFoundException, ServiceException {
		Boolean isUpgraded = studentService.upgradeStudent(filterOption);
		if (BooleanUtils.isTrue(isUpgraded)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student Upgrade Successfully", isUpgraded);
		} else {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Unable to Upgrade", isUpgraded);
		}
	}

	/**
	 * To find Whether the Enrollment Scheduled
	 * 
	 * @param id
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 */
	@GetMapping(value = "/{id}/check-schedule", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> findWhetherScheduleEnrolled(@PathVariable Long id)
			throws ServiceException {
		return ofNullable(studentService.findWhetherScheduleEnrolled(id))
				.map(isScheduleEnrolled -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Enrollments are Scheduled", isScheduleEnrolled))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Enrollment Schedule Not Found"));

	}

}
