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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.entity.CourseType;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.CourseTypeService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/course-type")
@CrossOrigin("*")
public class CourseTypeController {
	@Autowired
	private CourseTypeService courseTypeService;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getAllCourseTypes()throws ServiceException {
		List<CourseType> courseTypes = courseTypeService.getAllCourseTypes();
		if(CollectionUtils.isEmpty(courseTypes)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Course Types Not Found",new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Course Types retrieved Successfully", courseTypes);
	}
}
