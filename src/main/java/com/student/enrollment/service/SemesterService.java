package com.student.enrollment.service;

import java.util.List;
import com.student.enrollment.entity.Semester;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface SemesterService {
	/**
	 * 
	 * @param courseTypeId
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public List<Semester> getSemestersByCourseTypeId(Long courseTypeId) throws ServiceException, NotFoundException;

}
