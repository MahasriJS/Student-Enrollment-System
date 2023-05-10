package com.student.enrollment.controller;

import static java.util.Optional.ofNullable;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.student.enrollment.dto.HttpStatusResponse;
import com.student.enrollment.dto.StaffDTO;
import com.student.enrollment.entity.Staff;
import com.student.enrollment.exception.ConstraintException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.StaffService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping("/staffs")
@CrossOrigin("*")
public class StaffController {

	@Autowired
	private StaffService staffService;

	/**
	 * To Add Staff
	 * 
	 * @param staff
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws DuplicateException
	 * @throws NotFoundException
	 * @throws ConstraintException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> addStaff(@RequestBody @Valid StaffDTO staff)
			throws ServiceException, DuplicateException, NotFoundException, ConstraintException {
		staffService.addStaff(staff);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Added Successfully!!");
	}

	/**
	 * To get Staffs By Department Id
	 * 
	 * @param deptId
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@GetMapping(value = "department/{deptId}/staffs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getStaffsByDeptId(@PathVariable Long deptId)
			throws ServiceException, NotFoundException {
		List<Staff> staffs = staffService.getStaffsByDeptId(deptId);
		if (CollectionUtils.isEmpty(staffs)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staffs Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff retrieved Successfully", staffs);
	}

	/**
	 * To Get Staff By Id
	 * 
	 * @param staffId
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> getStaffById(@PathVariable("id") Long staffId)
			throws ServiceException, NotFoundException {
		return ofNullable(staffService.getStaffById(staffId))
				.map(staff -> ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff retrieved Successfully!!",
						staff))
				.orElse(ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Not Found", new ArrayList<>()));
	}

	/**
	 * To Update Staff Status
	 * 
	 * @param staffId
	 * @param isAvailable
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> updateStaffStatus(@PathVariable("id") Long staffId,
			@RequestParam(defaultValue = "true", required = false) Boolean isAvailable)
			throws ServiceException, NotFoundException {
		staffService.updateAvailability(staffId, isAvailable);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Status Updated Successfully");

	}

	/**
	 * To Update Staff By Id
	 * 
	 * @param id
	 * @param staff
	 * @return {@link ResponseEntity<HttpStatusResponse>}
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws DuplicateException
	 * @throws ConstraintException
	 */
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) // i--
	public ResponseEntity<HttpStatusResponse> updateStaffById(@PathVariable Long id, @RequestBody StaffDTO staff)
			throws ServiceException, NotFoundException, DuplicateException, ConstraintException {
		staffService.updateStaffById(id, staff);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Details update successfully");

	}

}