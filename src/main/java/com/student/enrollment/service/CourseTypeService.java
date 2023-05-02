package com.student.enrollment.service;

import java.util.List;
import com.student.enrollment.entity.CourseType;
import com.student.enrollment.exception.ServiceException;

public interface CourseTypeService {
	/**
	 * Fetching all course types
	 * 
	 * @return
	 * @throws ServiceException
	 */

	public List<CourseType> getAllCourseTypes() throws ServiceException;

}
