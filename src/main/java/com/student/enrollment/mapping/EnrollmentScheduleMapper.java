package com.student.enrollment.mapping;

import com.student.enrollment.dto.EnrollmentScheduleDTO;
import com.student.enrollment.entity.EnrollmentSchedule;


public class EnrollmentScheduleMapper {
	public static EnrollmentSchedule dtoToEntity(EnrollmentScheduleDTO enrollmentScheduleDto) {
		EnrollmentSchedule enrollmentSchedule= new EnrollmentSchedule();
		enrollmentSchedule.setId(enrollmentScheduleDto.getId());
		enrollmentSchedule.setAcademicYear(enrollmentScheduleDto.getAcademicYear());
		enrollmentSchedule.setIsStarted(enrollmentScheduleDto.getIsStarted());
		return enrollmentSchedule;
	}

}
