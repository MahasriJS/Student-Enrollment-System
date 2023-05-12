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
import com.student.enrollment.service.CourseTypeService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/course-types")
@CrossOrigin("*")
public class CourseTypeController {

	@Autowired
	private CourseTypeService courseTypeService;

	/**
	 * To get Course Types
	 * 
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getAllCourseTypes() throws ServiceException {

		return ofNullable(courseTypeService.getAllCourseTypes()).filter(CollectionUtils::isNotEmpty)
				.map(courseTypes -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Course Type retrieved Successfully", courseTypes))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Course Types Not Found",
						new ArrayList<>()));
	}
}
