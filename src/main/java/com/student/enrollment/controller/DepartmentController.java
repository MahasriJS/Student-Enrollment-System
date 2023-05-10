package com.student.enrollment.controller;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
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
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.DepartmentService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/departments")
@CrossOrigin("*")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	/**
	 * To get Departments
	 * 
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getAllDepartments() throws ServiceException {
		return ofNullable(departmentService.getAllDepartments()).filter(CollectionUtils::isNotEmpty)
				.map(departments -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Departments retrieved Successfully", departments))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Departments Not Found",
						new ArrayList<>()));
	}

	@Deprecated
	/**
	 * To get Department by Id
	 * 
	 * @param deptId
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getDepartmentById(@PathVariable("id") Long deptId)
			throws ServiceException, NotFoundException {
		return ofNullable(departmentService.getDepartmentById(deptId))
				.map(department -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Department retrieved Successfully", department))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Departments Not Found"));
	}

}