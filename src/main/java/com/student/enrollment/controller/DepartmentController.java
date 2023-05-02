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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.entity.Department;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.DepartmentService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/department")
@CrossOrigin("*")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getAllDepartments() throws ServiceException {
		List<Department> departments = departmentService.getAllDepartments();
		if (CollectionUtils.isEmpty(departments)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Departments Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Departments retrieved Successfully",
				departments);

	}
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE) // view Department by id 
	public ResponseEntity<HttpStatusResponse> getDepartmentById(@PathVariable("id") Long deptId)
			throws ServiceException, NotFoundException {
		Department department = departmentService.getDepartmentById(deptId);

		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Department retrieved Successfully", department);
	}

}