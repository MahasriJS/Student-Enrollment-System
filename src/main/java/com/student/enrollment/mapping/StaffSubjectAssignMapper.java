package com.student.enrollment.mapping;

import com.student.enrollment.dto.StaffSubjectAssignDTO;
import com.student.enrollment.entity.StaffSubjectAssign;

public class StaffSubjectAssignMapper {
	public static StaffSubjectAssign dtoToEntity(StaffSubjectAssignDTO staffSubjectAssignDto) {
		StaffSubjectAssign staffSubjectAssign= new StaffSubjectAssign();
		return staffSubjectAssign;

	}
}
