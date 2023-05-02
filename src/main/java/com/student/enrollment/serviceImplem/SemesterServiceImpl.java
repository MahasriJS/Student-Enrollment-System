package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.Objects;

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

	public List<Semester> getSemestersByCourseTypeId(Long courseTypeId) throws ServiceException, NotFoundException {
		try {
			if (Objects.nonNull(courseTypeId)) {
			return semRepository.findSemesterByCourseTypeId(courseTypeId);
			}else {
				throw new NotFoundException("Invalid Course Type Id");
			}
		}catch(NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
