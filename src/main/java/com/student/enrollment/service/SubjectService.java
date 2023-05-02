package com.student.enrollment.service;

import java.util.List;

import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.SubjectDTO;
import com.student.enrollment.entity.Subject;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface SubjectService {
	/**
	 * 
	 * @param subject
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException 
	 * @throws DuplicateException 
	 */
	public Subject addSubject(SubjectDTO subject) throws ServiceException, NotFoundException, DuplicateException;
	/**
	 * 
	 * @param filterOption
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	List<Subject> getSubjectsByCourseAndSemId(FilterOptionDTO filterOption) throws ServiceException, NotFoundException;
	/**
	 * 
	 * @param subjectId
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public Subject getSubjectById(Long subjectId) throws ServiceException, NotFoundException;

}
