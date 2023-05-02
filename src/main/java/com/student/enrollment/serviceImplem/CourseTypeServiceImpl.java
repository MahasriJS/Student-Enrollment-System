package com.student.enrollment.serviceImplem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.student.enrollment.entity.CourseType;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.repositorty.CourseTypeRepository;
import com.student.enrollment.service.CourseTypeService;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {

	@Autowired
	private CourseTypeRepository courseTypeRepository;

	@Override
	public List<CourseType> getAllCourseTypes() throws ServiceException {
		try {
			return courseTypeRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
