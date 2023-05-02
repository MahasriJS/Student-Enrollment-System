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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.dto.SubjectDTO;
import com.student.enrollment.entity.Subject;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.SubjectService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping
@CrossOrigin("*")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@PostMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<HttpStatusResponse> getSubjectsByCourseAndSemId(@RequestBody FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException {
		List<Subject> subjects = subjectService.getSubjectsByCourseAndSemId(filterOption);
		if (CollectionUtils.isEmpty(subjects)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Subjects Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Subjets retrieved Successfully!!", subjects);
	}

	@PostMapping(value="/subject",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> addSubject(@RequestBody @Valid SubjectDTO subject) throws ServiceException, NotFoundException, DuplicateException {
		subjectService.addSubject(subject);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Subject Added Successfully!!");
	}

	@GetMapping(value = "/subject/{id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<HttpStatusResponse> getSubjectById(@PathVariable("id") Long subjectId)
			throws ServiceException, NotFoundException {
		Subject subject = subjectService.getSubjectById(subjectId);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Subjet retrieved Successfully!!", subject);
	}

}
