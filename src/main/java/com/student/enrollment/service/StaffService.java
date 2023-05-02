package com.student.enrollment.service;

import java.util.List;

import com.student.enrollment.dto.StaffDTO;
import com.student.enrollment.dto.StaffSubjectAssignDTO;
import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.entity.Staff;
import com.student.enrollment.entity.StaffSubjectAssign;
import com.student.enrollment.entity.Student;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface StaffService {
	/**
	 * 
	 * @param staff
	 * @return
	 * @throws ServiceException
	 * @throws DuplicateException
	 * @throws NotFoundException 
	 */
	public Staff addStaff(StaffDTO staffDto) throws ServiceException, DuplicateException, NotFoundException;

	/**
	 * 
	 * @param deptId
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */

	List<Staff> getStaffsByDeptId(Long deptId) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param staffSubjectAssign
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException 
	 */
	public StaffSubjectAssign assignStaffToSubject(StaffSubjectAssignDTO staffSubjectAssigndto) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param id
	 * @param newAvailability
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public void updateAvailability(Long id, boolean newAvailability) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param id
	 * @param staff
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws DuplicateException
	 */
	public Staff updateStaffById(Long id, Staff staff) throws ServiceException, NotFoundException, DuplicateException;

	/**
	 * 
	 * @param staffId
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public Staff getStaffById(Long staffId) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param subjectId
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public List<Staff> getStaffsBySubjectId(Long subjectId) throws ServiceException, NotFoundException;
	/**
	 * 
	 * @param staffDto
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public Staff staffLogin(StaffDTO staffDto) throws ServiceException, NotFoundException;

}
