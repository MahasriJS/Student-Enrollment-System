package com.student.enrollment.service;

import java.util.List;
import com.student.enrollment.dto.EnrollmentDTO;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.entity.Enrollment;
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface EnrollmentService {
	/**
	 * 
	 * @param enrollmentId
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public EnrollmentDTO getEnrollmentDetails(Long enrollmentId) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param filterOption
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */

	public Long countStaffByDepartmentAndSubjectId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param filterOption
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public Long countStudentsByDepartmentCourseAndSemesterId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param filterOption
	 * @return
	 * @throws ServiceException
	 * @throws EnrollmentException
	 */
	public boolean enrollmentAvailability(FilterOptionDTO filterOption) throws ServiceException, EnrollmentException;

	/**
	 * 
	 * @param enrollment
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
//	public Enrollment enrollment(EnrollmentDTO enrollmentDto) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param enrollmentDto
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public List<EnrollmentDTO> getEnrollmentDetailsByAdmin(EnrollmentDTO enrollmentDto)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param enrollmentDto
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	List<EnrollmentDTO> getEnrollmentDetailsByStudent(EnrollmentDTO enrollmentDto)
			throws ServiceException, NotFoundException;

	List<Enrollment> enrollment(List<EnrollmentDTO> enrollmentDtos) throws ServiceException, NotFoundException;

}
