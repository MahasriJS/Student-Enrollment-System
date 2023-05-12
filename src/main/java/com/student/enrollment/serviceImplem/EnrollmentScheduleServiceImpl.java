package com.student.enrollment.serviceImplem;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.dto.EnrollmentScheduleDTO;
import com.student.enrollment.entity.Course;
import com.student.enrollment.entity.Department;
import com.student.enrollment.entity.EnrollmentSchedule;
import com.student.enrollment.entity.Semester;
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.mapping.EnrollmentScheduleMapper;
import com.student.enrollment.repositorty.CourseRepository;
import com.student.enrollment.repositorty.DepartmentRepository;
import com.student.enrollment.repositorty.EnrollmentScheduleRepository;
import com.student.enrollment.repositorty.SemesterRepository;
import com.student.enrollment.service.EnrollmentScheduleService;

@Service
public class EnrollmentScheduleServiceImpl implements EnrollmentScheduleService {
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private SemesterRepository semesterRepository;
	@Autowired
	private EnrollmentScheduleRepository enrollmentScheduleRepository;

	Logger logger = LoggerFactory.getLogger(EnrollmentScheduleServiceImpl.class);

	@Override
	public EnrollmentSchedule schdeduleEnrollment(EnrollmentScheduleDTO enrollmentScheduleDto)
			throws ServiceException, NotFoundException, EnrollmentException {
		try {
			logger.info("Schedule Enrollment");
			EnrollmentSchedule enrollmentSchedule = EnrollmentScheduleMapper.dtoToEntity(enrollmentScheduleDto);
			Long count = enrollmentScheduleRepository.countEnrollmentScheduletByDeptAndCourseAndAcademicYear(
					enrollmentScheduleDto.getAcademicYear(), enrollmentScheduleDto.getDeptId(),
					enrollmentScheduleDto.getCourseId(), enrollmentScheduleDto.getSemId());
			if (count >= 1) {
				throw new EnrollmentException("Already Scheduled");
			}
			if (Objects.nonNull(enrollmentScheduleDto) && Objects.nonNull(enrollmentScheduleDto.getDeptId())
					&& Objects.nonNull(enrollmentScheduleDto.getCourseId())
					&& Objects.nonNull(enrollmentScheduleDto.getSemId())
					&& Objects.nonNull(enrollmentScheduleDto.getAcademicYear())
					&& Objects.nonNull(enrollmentScheduleDto.getIsStarted())) {

				Department department = departmentRepository.findById(enrollmentScheduleDto.getDeptId()).orElse(null);
				enrollmentSchedule.setDepartment(department);
				Course course = courseRepository.findById(enrollmentScheduleDto.getCourseId()).orElse(null);
				enrollmentSchedule.setCourse(course);
				Semester semester = semesterRepository.findById(enrollmentScheduleDto.getSemId()).orElse(null);
				enrollmentSchedule.setSemester(semester);
				return enrollmentScheduleRepository.save(enrollmentSchedule);
			} else {
				throw new NotFoundException("Invalid Department Id or Course Id or Semester Id or academic year");
			}
		} catch (EnrollmentException e) {
			logger.error("Enrollment Scheduled already");
			throw new EnrollmentException(e.getMessage());
		} catch (NotFoundException e) {
			logger.error("Invalid Department Id or Course Id or Semester Id or academic year");
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}
}
