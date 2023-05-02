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
import com.student.enrollment.dto.StaffSubjectAssignDTO;
import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.entity.Staff;
import com.student.enrollment.entity.StaffSubjectAssign;
import com.student.enrollment.entity.Student;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.service.StaffService;
import com.student.enrollment.utils.ResponseUtils;

@RestController
@RequestMapping
@CrossOrigin("*")
public class StaffController {

	@Autowired
	private StaffService staffService;

	@PostMapping(value="/staff",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> addStaff(@RequestBody @Valid StaffDTO staff)
			throws ServiceException, DuplicateException, NotFoundException {
		staffService.addStaff(staff);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Added Successfully!!");
	}
	@GetMapping(value="department/{deptId}/staffs", produces = MediaType.APPLICATION_JSON_VALUE) // view staff by dept
	public ResponseEntity<HttpStatusResponse> getStaffsByDeptId(@PathVariable Long deptId)
			throws ServiceException, NotFoundException {
		List<Staff> staffs = staffService.getStaffsByDeptId(deptId);
		if (CollectionUtils.isEmpty(staffs)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staffs Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff retrieved Successfully", staffs);
	}
	@PostMapping(value="/admin-login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<HttpStatusResponse> login(@RequestBody StaffDTO staffDto) throws ServiceException, NotFoundException{
		Staff staff=staffService.staffLogin(staffDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Login Successfully!!",staff);
		
	}
	// view the assigned staff
	@GetMapping(value = "subjects/{subjectId}/staffs", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<HttpStatusResponse> getStaffsBySubjectId(@PathVariable Long subjectId)
			throws ServiceException, NotFoundException {
		List<Staff> staffs = staffService.getStaffsBySubjectId(subjectId);
		if (CollectionUtils.isEmpty(staffs)) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staffs Not Found", new ArrayList<>());
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff retrieved Successfully", staffs);

	}

	@PostMapping(value = "/staff-assign", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> assignStaffToSubject(@RequestBody StaffSubjectAssignDTO staffSubjectAssignDto)
			throws ServiceException, NotFoundException {
		StaffSubjectAssign assignedStaff = staffService.assignStaffToSubject(staffSubjectAssignDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Assigned Successfully!!", assignedStaff);

	}

	@GetMapping(value = "/staff/{id}", produces = MediaType.APPLICATION_JSON_VALUE) // view staff by id
	public ResponseEntity<HttpStatusResponse> getStaffById(@PathVariable("id") Long staffId)
			throws ServiceException, NotFoundException {
		Staff staff = staffService.getStaffById(staffId);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff retrieved Successfully!!", staff);

	}

	@PatchMapping(value = "/staff/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatusResponse> deleteStaff(@PathVariable("id") Long staffId, @RequestParam(defaultValue="true",required = false)  boolean isAvailable)
			throws ServiceException, NotFoundException {
		Staff staff = staffService.getStaffById(staffId);
		if (staff==null) {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Students Not Found", new ArrayList<>());
		}
		//boolean newAvailability = !staff.getIsAvailable();
		staffService.updateAvailability(staffId, isAvailable);
		//staffService.deleteStaff(staffId);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "Staff Deleted Successfully");

	}

	@PutMapping(value = "/staff/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) // i--
	public ResponseEntity<HttpStatusResponse> updateStaffById(@PathVariable Long id, @RequestBody Staff staff)
			throws ServiceException, NotFoundException, DuplicateException {
		staffService.updateStaffById(id, staff);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK.value(), "staff update successfully");

	}

}