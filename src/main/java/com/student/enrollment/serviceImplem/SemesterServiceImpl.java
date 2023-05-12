package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.entity.Semester;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.repositorty.SemesterRepository;
import com.student.enrollment.service.SemesterService;

@Service
public class SemesterServiceImpl implements SemesterService {

	@Autowired
	private SemesterRepository semRepository;

	Logger logger = LoggerFactory.getLogger(SemesterServiceImpl.class);

	public List<Semester> getSemestersByCourseTypeId(Long courseTypeId) throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching all Semester details by CourseType Id");
			if (Objects.nonNull(courseTypeId)) {
				return semRepository.findSemestersByCourseTypeId(courseTypeId);
			} else {
				throw new NotFoundException("Invalid Course Type Id");
			}
		} catch (NotFoundException e) {
			logger.error("Invalid Course Type Id");
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

}
