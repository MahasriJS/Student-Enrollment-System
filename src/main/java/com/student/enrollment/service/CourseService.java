package com.student.enrollment.service;

import java.util.List;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.entity.Course;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface CourseService {
	/**
	 * 
	 * @param filterOption
	 * @return List of {@link Course}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */

	List<Course> getCoursesByDeptAndCourseTypeId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException;

}
