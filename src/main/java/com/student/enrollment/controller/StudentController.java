package com.student.enrollment.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.entity.Student;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.StudentService;
import com.student.enrollment.utils.ResponseUtils;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping
@CrossOrigin("*")
public class StudentController {
	@Autowired
	private StudentService studService;

	
	@PostMapping(value="/student",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> addStudent(@RequestBody @Valid StudentDTO student)
			throws ServiceException, DuplicateException, NotFoundException {
		studService.addStudent(student);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student Added Successfully!!");
	}

	@GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE) // view Student by id
	public ResponseEntity<HttpStatusResponse> getStudentById(@PathVariable("id") Long studId)
			throws ServiceException, NotFoundException {
		Student student = studService.getStudentById(studId);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student retrieved Successfully", student);
	}

	@PatchMapping(value = "/student/{id}")
	public ResponseEntity<HttpStatusResponse> deleteStudent(@PathVariable("id") Long id, @RequestParam(defaultValue="true",required = false)  boolean isAvailable)
			throws ServiceException, NotFoundException {
		Student student = studService.getStudentById(id);
		if (student==null) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Students Not Found", new ArrayList<>());
		}
		studService.updateAvailability(id, isAvailable);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student Deleted Successfully");
	}

	@PutMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> updateStudentById(@PathVariable Long id, @RequestBody Student student)
			throws ServiceException, NotFoundException, DuplicateException {
		studService.updateStudentById(id, student);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Student updated Successfully");
	}

	@PostMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getStudentsByDeptAndCourseAndSemId(
			@RequestBody FilterOptionDTO filterOption) throws ServiceException, NotFoundException {
		List<Student> students = studService.getStudentsByDeptAndCourseAndSemId(filterOption);
		if (CollectionUtils.isEmpty(students)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Students Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Students retrieved Successfully!!", students);

	}
	@PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<HttpStatusResponse> login(@RequestBody StudentDTO studentDto) throws ServiceException, NotFoundException{
		Student student=studService.studentLogin(studentDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Login Successfully!!",student);
		
	}
	@PostMapping(value="/change-password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<HttpStatusResponse> changePassword(@RequestBody FilterOptionDTO filterOption) throws ServiceException, NotFoundException{
		studService.changePassword(filterOption);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Password Successfully Changed!!");
		
	}
}