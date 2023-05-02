package com.student.enrollment.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.entity.Course;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.CourseService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/course")
@CrossOrigin("*")
public class CourseController {

	@Autowired
	private CourseService courseService;
	//Get All Courses here
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getCoursesByDeptAndCoursTypeId(@RequestBody @Valid FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException {
		List<Course> courses = courseService.getCoursesByDeptAndCoursTypeId(filterOption);
		if(CollectionUtils.isEmpty(courses)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Course Not Found",new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Course retrieved Successfully", courses);
		
	}
}