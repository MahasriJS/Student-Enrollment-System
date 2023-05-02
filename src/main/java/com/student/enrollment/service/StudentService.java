package com.student.enrollment.service;

import java.util.List;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.entity.Student;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.InvalidPhoneNumberException;
import com.student.enrollment.exception.ServiceException;

public interface StudentService {
	/**
	 * 
	 * @param student
	 * @return
	 * @throws ServiceException
	 * @throws DuplicateException
	 * @throws InvalidPhoneNumberException
	 * @throws NotFoundException
	 */
	public Student addStudent(StudentDTO studentDto) throws ServiceException, DuplicateException, NotFoundException;

	/**
	 * 
	 * @param studId
	 * @return
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
	public void updateAvailability(Long id, boolean newAvailability) throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param id
	 * @param student
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 * @throws DuplicateException
	 */
	public Student updateStudentById(Long id, Student student)
			throws ServiceException, NotFoundException, DuplicateException;

	/**
	 * 
	 * @param filterOption
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public List<Student> getStudentsByDeptAndCourseAndSemId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException;

	/**
	 * 
	 * @param studentDto
	 * @return 
	 * @throws NotFoundException
	 * @throws ServiceException
	 */

	public Student studentLogin(StudentDTO studentDto) throws ServiceException, NotFoundException;
	/**
	 * 
	 * @param filterOption
	 * @return
	 * @throws NotFoundException
	 * @throws ServiceException
	 */

	public boolean changePassword(FilterOptionDTO filterOption) throws NotFoundException, ServiceException;
	
}
