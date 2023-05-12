package com.student.enrollment.service;

import java.util.List;

import com.student.enrollment.dto.ChangePaswordDTO;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.entity.Student;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ConstraintException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.InvalidPhoneNumberException;
import com.student.enrollment.exception.ServiceException;

public interface StudentService {
	/**
	 * 
	 * @param student
	 * @return {@link Student}
	 * @throws ServiceException
	 * @throws DuplicateException
	 * @throws InvalidPhoneNumberException
	 * @throws NotFoundException
	 * @throws ConstraintException
	 */
	public Student addStudent(StudentDTO studentDto)
			throws ServiceException, DuplicateException, NotFoundException, ConstraintException;

	/**
	 * 
	 * @param studId
	 * @return {@link Student}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public Student getStudentById(Long studId) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param id
	 * @param newAvailability
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public void updateAvailability(Long studentId, Boolean newAvailability) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param id
	 * @param student
	 * @return {@link Student}
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws DuplicateException
	 * @throws ConstraintException
	 */
	public Student updateStudentById(Long studentId, StudentDTO student)
			throws ServiceException, NotFoundException, DuplicateException, ConstraintException;

	/**
	 * 
	 * @param filterOption
	 * @return List of {@link Student}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public List<Student> getStudentsByDeptAndCourseAndAcademicYear(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param changePaswordDto
	 * @return {@link Boolean}
	 * @throws NotFoundException
	 * @throws ServiceException
	 */

	public boolean changePassword(ChangePaswordDTO changePaswordDto) throws NotFoundException, ServiceException;

	/**
	 * 
	 * @param filterOption
	 * @return {@link Boolean}
	 * @throws NotFoundException
	 * @throws ServiceException
	 */

	public Boolean upgradeStudent(FilterOptionDTO filterOption) throws NotFoundException, ServiceException;

	/**
	 * Fetch Distinct Academic Year
	 * 
	 * @return List of {@link String}
	 * @throws ServiceException
	 */

	public List<String> getAllAcademicYear() throws ServiceException;

	/**
	 * 
	 * @param studentId
	 * @return {@link Boolean}
	 * @throws ServiceException
	 */

	public Boolean findWhetherScheduleEnrolled(Long studentId) throws ServiceException;
}
