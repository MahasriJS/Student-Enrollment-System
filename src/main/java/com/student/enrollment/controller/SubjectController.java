package com.student.enrollment.controller;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
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
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.SubjectService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/subjects")
@CrossOrigin("*")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	/**
	 * To get Subjects By Course and Semester Id
	 * 
	 * @param filterOption
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PostMapping(value = "/ids", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<HttpStatusResponse> getSubjectsByCourseAndSemId(@RequestBody FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException {

		return ofNullable(subjectService.getSubjectsByCourseAndSemId(filterOption)).filter(CollectionUtils::isNotEmpty)
				.map(subjects -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Subjets retrieved Successfully!!", subjects))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Subjects Not Found",
						new ArrayList<>()));
	}

	/**
	 * To add Subject
	 * 
	 * @param subject
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws DuplicateException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> addSubject(@RequestBody @Valid SubjectDTO subject)
			throws ServiceException, NotFoundException, DuplicateException {
		subjectService.addSubject(subject);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Subject Added Successfully!!");
	}

	/**
	 * 
	 * @param subjectId
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@Deprecated
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getSubjectById(@PathVariable("id") Long subjectId)
			throws ServiceException, NotFoundException {
		return ofNullable(subjectService.getSubjectById(subjectId))
				.map(subject -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Subject retrieved Successfully!!", subject))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Subject Not Found",
						new ArrayList<>()));
	}

}
