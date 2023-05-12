package com.student.enrollment.controller;

import java.util.ArrayList;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static java.util.Optional.ofNullable;

import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.CourseService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/courses")
@CrossOrigin("*")
public class CourseController {

	@Autowired
	private CourseService courseService;

	/**
	 * To get the courses by the department and course type id
	 *
	 * @param filterOption
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getCoursesByDeptAndCoursTypeId(
			@RequestBody @Valid FilterOptionDTO filterOption) throws ServiceException, NotFoundException {

		return ofNullable(courseService.getCoursesByDeptAndCourseTypeId(filterOption))
				.filter(CollectionUtils::isNotEmpty)
				.map(courses -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Course retrieved Successfully",
						courses))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Course Not Found", new ArrayList<>()));

	}
}