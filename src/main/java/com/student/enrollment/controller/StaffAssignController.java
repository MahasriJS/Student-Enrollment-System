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

import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.dto.StaffSubjectAssignDTO;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.StaffService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/subjects/{subjectId}/staffs/assign")
@CrossOrigin("*")

public class StaffAssignController {
	@Autowired
	private StaffService staffService;

	/**
	 * To get Assigned Staff By Subject Id
	 * 
	 * @param subjectId
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getStaffsBySubjectId(@PathVariable Long subjectId)
			throws ServiceException, NotFoundException {
		return ofNullable(staffService.getStaffsBySubjectId(subjectId)).filter(CollectionUtils::isNotEmpty)
				.map(staffs -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staffs retrieved Successfully",
						staffs))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Not Found", new ArrayList<>()));
	}

	/**
	 * To Assign Staff to the Subject
	 * 
	 * @param staffSubjectAssignDto
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> saveStaffAssignment(@PathVariable Long subjectId,
			@RequestBody @Valid StaffSubjectAssignDTO staffSubjectAssignDto)
			throws ServiceException, NotFoundException {
		staffService.saveStaffAssignment(staffSubjectAssignDto, subjectId);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Assigned Successfully!!");

	}

}
