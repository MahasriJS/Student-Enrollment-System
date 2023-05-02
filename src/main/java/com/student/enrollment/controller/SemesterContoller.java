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
import com.student.enrollment.entity.Semester;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.SemesterService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("courseType/{courseTypeId}/semester")
@CrossOrigin("*") 
public class SemesterContoller {

	@Autowired
	private SemesterService semesterService;

	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getSemestersByCourseTypeId(@PathVariable("courseTypeId") Long courseTypeId)
			throws ServiceException, NotFoundException {
		List<Semester> semesters = semesterService.getSemestersByCourseTypeId(courseTypeId);
		if (CollectionUtils.isEmpty(semesters)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Semesters Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Semester retrieved Successfully", semesters);
	}

}