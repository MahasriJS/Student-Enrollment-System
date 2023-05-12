package com.student.enrollment.serviceImplem;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(CourseTypeServiceImpl.class);

	@Override
	public List<CourseType> getAllCourseTypes() throws ServiceException {

		try {
			logger.info("Fetching all CourseTypes");
			return courseTypeRepository.findAll();
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

}
