package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.dto.EnrollmentDTO;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.entity.Enrollment;

import com.student.enrollment.entity.Staff;
import com.student.enrollment.entity.Student;
import com.student.enrollment.entity.Subject;
import com.student.enrollment.exception.EnrollmentException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.mapping.EnrollmentMapper;
import com.student.enrollment.repositorty.EnrollmentRepository;
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
	public List<EnrollmentDTO> getEnrollmentDetailsByStudent(EnrollmentDTO enrollmentDto)
			throws ServiceException, NotFoundException {
		try {
			List<EnrollmentDTO> enrollments = enrollmentRepository
					.getEnrollmentDetailsByStudent(enrollmentDto.getSemId(), enrollmentDto.getStudentId());
			return enrollments;
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Semester Id Not Found");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

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
	public List<Enrollment> enrollment(List<EnrollmentDTO> enrollmentDtos) throws ServiceException, NotFoundException {
		try {
			
			List<Enrollment> enrollments = EnrollmentMapper.dtoToEntity(enrollmentDtos);
			for(EnrollmentDTO enrollmentDto :enrollmentDtos) {
			if (Objects.nonNull(enrollmentDto) && Objects.nonNull(enrollmentDto.getSubjectId())
					&& Objects.nonNull(((EnrollmentDTO) enrollmentDto).getStaffId())
					&& Objects.nonNull(enrollmentDto.getStudentId()) && enrollmentDto.getSubjectId()>=1 && enrollmentDto.getStaffId()>=1
					&& enrollmentDto.getStudentId()>=1) {
				Enrollment enroll=new Enrollment();
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
			System.out.println(enrollments.toString());
			return enrollmentRepository.saveAllAndFlush(enrollments);
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean enrollmentAvailability(FilterOptionDTO filterOption) throws ServiceException, EnrollmentException {
		try {
			Long studentCount = studentRepository.countStudentsByDepartmentCourseAndSemesterId(filterOption.getDeptId(),
					filterOption.getCourseId(), filterOption.getSemId());
			Long staffCount = staffRepository.countStaffByDepartmentAndSubjectId(filterOption.getDeptId(),
					filterOption.getSubjectId());
			Long enrollmentCount = enrollmentRepository
					.countEnrollmentBySubjectAndStaffById(filterOption.getSubjectId(), filterOption.getStaffId());
			Long count = enrollmentRepository.existingEnrollments(filterOption.getSubjectId(),
					filterOption.getStudentId());
			Long availability = studentCount / staffCount;

			if (count >= 6) {
				throw new EnrollmentException("Student is already enrolled in this subject");
			}
			if (enrollmentCount > availability) {
				return false;
				//throw new EnrollmentException("Enrollment is full for this staff");
			}

			return true;
		} catch (EnrollmentException e) {
			throw new EnrollmentException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public List<EnrollmentDTO> getEnrollmentDetailsByAdmin(EnrollmentDTO enrollmentDto)
			throws ServiceException, NotFoundException {
		try {
			if (Objects.nonNull(enrollmentDto) && Objects.nonNull(enrollmentDto.getDeptId())
					&& Objects.nonNull(enrollmentDto.getCourseId()) && Objects.nonNull(enrollmentDto.getSemId())
					&& Objects.nonNull(enrollmentDto.getSubjectId())&& Objects.nonNull(enrollmentDto.getStaffId())) {
				return enrollmentRepository.getEnrollmentDetailsByAdmin(enrollmentDto.getDeptId(),
						enrollmentDto.getCourseId(), enrollmentDto.getSemId(), enrollmentDto.getSubjectId(),
						enrollmentDto.getStaffId());
			}else {
				throw new NotFoundException("Invalid Department Id or Course Id or Semester Id or Subject Id or Staff Id");
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
