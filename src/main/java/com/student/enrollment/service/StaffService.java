package com.student.enrollment.service;

import java.util.List;
import com.student.enrollment.dto.StaffDTO;
import com.student.enrollment.dto.StaffSubjectAssignDTO;
import com.student.enrollment.entity.Staff;
import com.student.enrollment.entity.StaffSubjectAssign;
import com.student.enrollment.exception.ConstraintException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface StaffService {
	/**
	 * 
	 * @param staff
	 * @return {@link Staff}
	 * @throws ServiceException
	 * @throws DuplicateException
	 * @throws NotFoundException
	 * @throws ConstraintException
	 */
	public Staff addStaff(StaffDTO staffDto)
			throws ServiceException, DuplicateException, NotFoundException, ConstraintException;

	/**
	 * 
	 * @param deptId
	 * @return List of {@link Staff}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */

	List<Staff> getStaffsByDeptId(List<Long> deptIds) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param staffSubjectAssign
	 * @return {@link StaffSubjectAssign}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public StaffSubjectAssign saveStaffAssignment(StaffSubjectAssignDTO staffSubjectAssigndto, Long subjectId)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param id
	 * @param newAvailability
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public void updateAvailability(Long staffId, Boolean newAvailability) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param id
	 * @param staff
	 * @return {@link Staff}
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws DuplicateException
	 * @throws ConstraintException
	 */
	public Staff updateStaffById(Long staffId, StaffDTO staff)
			throws ServiceException, NotFoundException, DuplicateException, ConstraintException;

	/**
	 * 
	 * @param staffId
	 * @return {@link Staff}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public Staff getStaffById(Long staffId) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param subjectId
	 * @return List of {@link Staff}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public List<Staff> getStaffsBySubjectId(Long subjectId) throws ServiceException, NotFoundException;

}
