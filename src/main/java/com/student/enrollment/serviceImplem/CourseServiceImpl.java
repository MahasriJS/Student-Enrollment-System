package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.entity.Course;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.repositorty.CourseRepository;
import com.student.enrollment.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public List<Course> getCoursesByDeptAndCoursTypeId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException {
		try {
			if (Objects.nonNull(filterOption) &&  Objects.nonNull(filterOption.getDeptId())
					&& Objects.nonNull(filterOption.getCourseTypeId())) {
				List<Course> courses = courseRepository.getCoursesByDeptAndCoursTypeId(filterOption.getDeptId(),
						filterOption.getCourseTypeId());
				return courses;
			} else {
				throw new NotFoundException("Invalid department id or course type id");
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
