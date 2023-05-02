package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.entity.Course;
import com.student.enrollment.entity.Department;
import com.student.enrollment.entity.Semester;
import com.student.enrollment.entity.Student;
import com.student.enrollment.entity.UserType;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.mapping.StudentMapper;
import com.student.enrollment.repositorty.CourseRepository;
import com.student.enrollment.repositorty.DepartmentRepository;
import com.student.enrollment.repositorty.SemesterRepository;
import com.student.enrollment.repositorty.StudentRepository;
import com.student.enrollment.repositorty.UserTypeRepository;
import com.student.enrollment.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private SemesterRepository semesterRepository;
	@Autowired
	private UserTypeRepository userTypeRepository;

	public Student addStudent(StudentDTO studentDto) throws ServiceException, DuplicateException, NotFoundException {
		try {
			Student student = StudentMapper.dtoToEntity(studentDto);
			String email = student.getEmail();
			String phno = student.getPhno();
			if (studentRepository.existsByEmail(email) == true) {
				throw new DuplicateException("Email address is already exists.");
			}
			if (studentRepository.existsByPhno(phno) == true) {
				throw new DuplicateException("Phone Number already exists.");
			}
			if (Objects.nonNull(studentDto) && Objects.nonNull(studentDto.getDeptId())
					&& Objects.nonNull(studentDto.getCourseId()) && Objects.nonNull(studentDto.getSemId())) {
				Department department = departmentRepository.findById(studentDto.getDeptId()).orElse(null);
				student.setDepartment(department);
				Course course = courseRepository.findById(studentDto.getCourseId()).orElse(null);
				student.setCourse(course);
				Semester semester = semesterRepository.findById(studentDto.getSemId()).orElse(null);
				student.setSemester(semester);
				UserType studentType = userTypeRepository.findById(userTypeRepository.findStudentUserTypeByName())
						.orElse(null);
				student.setUserType(studentType);
				return studentRepository.save(student);
			} else {
				throw new NotFoundException("Invalid Department Id or Course Id or Semester Id");
			}
		} catch (DuplicateException e) {
			throw new DuplicateException(e.getMessage());
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public Student getStudentById(Long studId) throws ServiceException, NotFoundException {
		try {
			return studentRepository.findById(studId).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("student not found");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void updateAvailability(Long id, boolean isAvailable) throws ServiceException, NotFoundException {
		try {
			studentRepository.updateStudentBasedOnAvailablity(id, isAvailable);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException("student not found");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Student updateStudentById(Long id, Student student)
			throws ServiceException, NotFoundException, DuplicateException {
		try {
			Student existingStudent = studentRepository.findById(id).get();
			existingStudent.setName(student.getName());
			existingStudent.setAddress(student.getAddress());
			existingStudent.setDob(student.getDob());
			existingStudent.setDateOfJoining(student.getDateOfJoining());
			existingStudent.setEmail(student.getEmail());
			existingStudent.setPhno(student.getPhno());
			existingStudent.setIsAvailable(student.getIsAvailable());

			return studentRepository.save(existingStudent);
		} catch (NoSuchElementException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<Student> getStudentsByDeptAndCourseAndSemId(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException {
		try {
			if (Objects.nonNull(filterOption) && Objects.nonNull(filterOption.getDeptId())
					&& Objects.nonNull(filterOption.getCourseId()) && Objects.nonNull(filterOption.getSemId())) {
				return studentRepository.getStudentByDeptAndCourseAndSemId(filterOption.getDeptId(),
						filterOption.getCourseId(), filterOption.getSemId());
			} else {
				throw new NotFoundException("Invalid Department Id or Course Id or Semester Id");
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Student studentLogin(StudentDTO studentDto) throws ServiceException, NotFoundException {
		try {

			String email = studentDto.getEmail();
			String password = studentDto.getPassword();
			if (Objects.nonNull(studentDto) && Objects.nonNull(studentDto.getEmail())
					&& Objects.nonNull(studentDto.getPassword()) && studentRepository.existsByEmail(email) == true
					&& studentRepository.existsByPassword(password) == true) {
				return studentRepository.getStudentByEmailAndPassword(email, studentDto.getPassword());
			} else {
				throw new NotFoundException("Invalid username or password");
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public boolean changePassword(FilterOptionDTO filterOption) throws NotFoundException, ServiceException {
		try {

			if (Objects.nonNull(filterOption) && Objects.nonNull(filterOption.getEmail())
					&& Objects.nonNull(filterOption.getNewPassword())
					&& Objects.nonNull(filterOption.getConfirmPassword())
					&& filterOption.getNewPassword().equals(filterOption.getConfirmPassword())) {
				studentRepository.changePassword(filterOption.getEmail(), filterOption.getNewPassword());
				return true;

			} else {
				throw new NotFoundException("Invalid password");
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
