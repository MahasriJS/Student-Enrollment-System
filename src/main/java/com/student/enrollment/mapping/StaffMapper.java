package com.student.enrollment.mapping;

import com.student.enrollment.dto.StaffDTO;
import com.student.enrollment.entity.Staff;

public class StaffMapper {
	public static Staff dtoToEntity(StaffDTO staffDto) {
		Staff staff= new Staff();
		staff.setName(staffDto.getName());
		staff.setDob(staffDto.getDob());
		staff.setDateOfJoining(staffDto.getDateOfJoining());
		staff.setAddress(staffDto.getAddress());
		staff.setSalary(staffDto.getSalary());
		staff.setDesignation(staffDto.getDesignation());
		staff.setEmail(staffDto.getEmail());
		staff.setPhno(staffDto.getPhno());
		staff.setPassword(staffDto.getPassword());
		return staff;
	}
}
