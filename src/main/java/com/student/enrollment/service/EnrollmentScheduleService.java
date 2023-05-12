package com.student.enrollment.service;

import com.student.enrollment.dto.EnrollmentScheduleDTO;
import com.student.enrollment.entity.EnrollmentSchedule;
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface EnrollmentScheduleService {
	/**
	 * 
	 * @param enrollmentScheduleDto
	 * @return { {@link EnrollmentSchedule }
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws EnrollmentException
	 */
	public EnrollmentSchedule schdeduleEnrollment(EnrollmentScheduleDTO enrollmentScheduleDto)
			throws ServiceException, NotFoundException, EnrollmentException;
}
