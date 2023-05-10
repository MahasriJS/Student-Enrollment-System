package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.dto.EnrollmentDTO;
import com.student.enrollment.dto.EnrollmentScheduleDTO;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.entity.Course;
import com.student.enrollment.entity.Department;
import com.student.enrollment.entity.Enrollment;
import com.student.enrollment.entity.EnrollmentSchedule;
import com.student.enrollment.entity.Semester;
import com.student.enrollment.entity.Staff;
import com.student.enrollment.entity.Student;
import com.student.enrollment.entity.Subject;
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.mapping.EnrollmentMapper;
import com.student.enrollment.mapping.EnrollmentScheduleMapper;
import com.student.enrollment.mapping.StudentMapper;
import com.student.enrollment.repositorty.CourseRepository;
import com.student.enrollment.repositorty.DepartmentRepository;
import com.student.enrollment.repositorty.EnrollmentRepository;
import com.student.enrollment.repositorty.SemesterRepository;
import com.student.enrollment.repositorty.StaffRepository;
import com.student.enrollment.repositorty.StudentRepository;
import com.student.enrollment.repositorty.SubjectRepository;
import com.student.enrollment.service.EnrollmentService;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

	@Deprecated
	public EnrollmentDTO getEnrollmentDetails(Long enrollmentId) throws ServiceException, NotFoundException {
		try {
			return enrollmentRepository.getEnrollmentDetails(enrollmentId);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Enrollment Not Found");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<EnrollmentDTO> getEnrollmentDetailsForStudentView(EnrollmentDTO enrollmentDto)
			throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching Enrollments details for Student");
			List<EnrollmentDTO> enrollments = enrollmentRepository
					.getEnrollmentDetailsByStudent(enrollmentDto.getSemId(), enrollmentDto.getStudentId());
			return enrollments;
		} catch (NoSuchElementException e) {
			logger.error("Semester or Student Id Not Found");
			throw new NotFoundException("Semester or Student Id Not Found");
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	@Deprecated
	public Long countStaffByDepartmentAndSubjectId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException {
		try {
			Long staffCount = staffRepository.countStaffByDepartmentAndSubjectId(filterOption.getDeptId(),
					filterOption.getSubjectId());
			if (staffCount == 0) {
				throw new NotFoundException("staff not found");
			}
			return staffCount;
		} catch (NotFoundException e) {
			throw new NotFoundException("staff not found");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Deprecated
	public Long countStudentsByDepartmentCourseAndSemesterId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException {
		try {
			Long studentCount = studentRepository.countStudentsByDepartmentCourseAndSemesterId(filterOption.getDeptId(),
					filterOption.getCourseId(), filterOption.getSemId());
			if (studentCount == 0) {
				throw new NotFoundException("student not found");
			}
			return studentCount;
		} catch (NotFoundException e) {
			throw new NotFoundException("student not found");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void saveEnrollments(List<EnrollmentDTO> enrollmentDtos)
			throws ServiceException, NotFoundException, EnrollmentException {
		try {
			logger.info("Enrollments for Student");
			List<Enrollment> enrollments = EnrollmentMapper.dtoToEntity(enrollmentDtos);
			for (EnrollmentDTO enrollmentDto : enrollmentDtos) {
				if (Objects.nonNull(enrollmentDto) && Objects.nonNull(enrollmentDto.getSubjectId())
						&& Objects.nonNull(((EnrollmentDTO) enrollmentDto).getStaffId())
						&& Objects.nonNull(enrollmentDto.getStudentId()) && enrollmentDto.getSubjectId() >= 1
						&& enrollmentDto.getStaffId() >= 1 && enrollmentDto.getStudentId() >= 1) {
					Long count = enrollmentRepository.existingEnrollments(enrollmentDto.getSubjectId(),
							enrollmentDto.getStudentId());
					if (count >= 1) {
						throw new EnrollmentException("Student is already enrolled in this subject");
					}
					Enrollment enroll = new Enrollment();
					Subject subject = subjectRepository.findById(enrollmentDto.getSubjectId()).orElse(null);
					enroll.setSubject(subject);
					Student student = studentRepository.findById(enrollmentDto.getStudentId()).orElse(null);
					enroll.setStudent(student);
					Staff staff = staffRepository.findById(enrollmentDto.getStaffId()).orElse(null);
					enroll.setStaff(staff);
					enrollments.add(enroll);
				} else {
					throw new NotFoundException("Invalid Student Id or Subject Id or Staff Id");
				}
			}
			enrollmentRepository.saveAll(enrollments);
		} catch (EnrollmentException e) {
			logger.error("Student is already enrolled in this subject");
			throw new EnrollmentException(e.getMessage());
		} catch (NotFoundException e) {
			logger.error("Invalid Student Id or Subject Id or Staff Id");
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	public Boolean isEnrollmentAvailable(FilterOptionDTO filterOption) throws ServiceException, EnrollmentException {
		try {
			logger.info("Checking Enrollments Availability for Student");
			Long studentCount = studentRepository.countStudentsByDepartmentCourseAndSemesterId(filterOption.getDeptId(),
					filterOption.getCourseId(), filterOption.getSemId());
			Long staffCount = staffRepository.countStaffByDepartmentAndSubjectId(filterOption.getDeptId(),
					filterOption.getSubjectId());
			Long enrollmentCount = enrollmentRepository
					.countEnrollmentBySubjectAndStaffById(filterOption.getSubjectId(), filterOption.getStaffId());
			Long availability = studentCount / staffCount;
			if (enrollmentCount > availability) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public List<EnrollmentDTO> getEnrollmentDetailsForAdminView(EnrollmentDTO enrollmentDto)
			throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching Enrollments details for Admin");
			if (Objects.nonNull(enrollmentDto) && Objects.nonNull(enrollmentDto.getDeptId())
					&& Objects.nonNull(enrollmentDto.getCourseId()) && Objects.nonNull(enrollmentDto.getSemId())
					&& Objects.nonNull(enrollmentDto.getSubjectId()) && Objects.nonNull(enrollmentDto.getStaffId())
					&& Objects.nonNull(enrollmentDto.getAcademicYear())) {

				return enrollmentRepository.getEnrollmentDetailsByAdmin(enrollmentDto.getDeptId(),
						enrollmentDto.getCourseId(), enrollmentDto.getAcademicYear(), enrollmentDto.getSemId(),
						enrollmentDto.getSubjectId(), enrollmentDto.getStaffId());
			} else {
				throw new NotFoundException(
						"Invalid Department Id or Course Id or Semester Id or Subject Id or Staff Id");
			}
		} catch (NotFoundException e) {
			logger.error("Invalid Department Id or Course Id or Semester Id or Subject Id or Staff Id");
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

}
