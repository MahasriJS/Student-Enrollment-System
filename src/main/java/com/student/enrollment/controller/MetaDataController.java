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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.StudentService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/meta-data")
@CrossOrigin("*")
public class MetaDataController {

	@Autowired
	private StudentService studentService;

	/**
	 * To Get All Academic Years
	 * 
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 */
	@GetMapping(value = "/academic-year", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getAllAcademicYear() throws ServiceException {
		return ofNullable(studentService.getAllAcademicYear()).filter(CollectionUtils::isNotEmpty)
				.map(academicYears -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Academic Years Retrieved  Successfully", academicYears))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Academic Years Not Found",
						new ArrayList<>()));
	}

}
