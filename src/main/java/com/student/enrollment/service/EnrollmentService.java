package com.student.enrollment.service;

import java.util.List;
import com.student.enrollment.dto.EnrollmentDTO;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface EnrollmentService {
	/**
	 * 
	 * @param enrollmentId
	 * @return {@link EnrollmentDTO}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public EnrollmentDTO getEnrollmentDetails(Long enrollmentId) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param filterOption
	 * @return {@link Long}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */

	public Long countStaffByDepartmentAndSubjectId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param filterOption
	 * @return {@link Long}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public Long countStudentsByDepartmentCourseAndSemesterId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param filterOption
	 * @return {@link Boolean}
	 * @throws ServiceException
	 * @throws EnrollmentException
	 */
	public Boolean isEnrollmentAvailable(FilterOptionDTO filterOption) throws ServiceException;

	/**
	 * 
	 * @param enrollmentDto
	 * @return List of {@link EnrollmentDTO}
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws EnrollmentException
	 */
	public List<EnrollmentDTO> getEnrollmentDetailsForAdminView(EnrollmentDTO enrollmentDto)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param enrollmentDto
	 * @return List of {@link EnrollmentDTO}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	List<EnrollmentDTO> getEnrollmentDetailsForStudentView(EnrollmentDTO enrollmentDto)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param enrollmentDtos
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws EnrollmentException
	 */
	void saveEnrollments(List<EnrollmentDTO> enrollmentDtos)
			throws ServiceException, NotFoundException, EnrollmentException;

}
