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
import com.student.enrollment.service.SemesterService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("courseTypes/{courseTypeId}/semesters")
@CrossOrigin("*")
public class SemesterContoller {

	@Autowired
	private SemesterService semesterService;

	/**
	 * To get Semesters by Course Type Id
	 * 
	 * @param courseTypeId
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getSemestersByCourseTypeId(
			@PathVariable("courseTypeId") Long courseTypeId) throws ServiceException, NotFoundException {
		return ofNullable(semesterService.getSemestersByCourseTypeId(courseTypeId)).filter(CollectionUtils::isNotEmpty)
				.map(semesters -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(),
						"Semesters retrieved Successfully", semesters))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Semesters Not Found",
						new ArrayList<>()));

	}
}