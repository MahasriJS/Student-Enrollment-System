package com.student.enrollment.serviceImplem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.dto.ChangePaswordDTO;
import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.entity.Course;
import com.student.enrollment.entity.Department;
import com.student.enrollment.entity.Enrollment;
import com.student.enrollment.entity.Semester;
import com.student.enrollment.entity.Student;
import com.student.enrollment.entity.Subject;
import com.student.enrollment.entity.UserType;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ConstraintException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.mapping.StudentMapper;
import com.student.enrollment.repositorty.CourseRepository;
import com.student.enrollment.repositorty.DepartmentRepository;
import com.student.enrollment.repositorty.EnrollmentRepository;
import com.student.enrollment.repositorty.EnrollmentScheduleRepository;
import com.student.enrollment.repositorty.SemesterRepository;
import com.student.enrollment.repositorty.StudentRepository;
import com.student.enrollment.repositorty.SubjectRepository;
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
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private EnrollmentScheduleRepository enrollmentScheduleRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Override
	public Student addStudent(StudentDTO studentDto)
			throws ServiceException, DuplicateException, NotFoundException, ConstraintException {
		try {
			Student student = StudentMapper.dtoToEntity(studentDto);
			if (BooleanUtils.isTrue(studentRepository.existsByEmail(student.getEmail()))) {
				throw new DuplicateException("Email address is already exists.");
			}
			if (BooleanUtils.isTrue(studentRepository.existsByPhno(student.getPhno()))) {
				throw new DuplicateException("Phone Number already exists.");
			}
			if (Objects.nonNull(studentDto) && Objects.nonNull(studentDto.getDeptId())
					&& Objects.nonNull(studentDto.getCourseId()) && Objects.nonNull(studentDto.getSemId())) {
				student.setPassword(studentDto.getDob().toString());
				Department department = departmentRepository.findById(studentDto.getDeptId()).orElse(null);
				student.setDepartment(department);
				Course course = courseRepository.findById(studentDto.getCourseId()).orElse(null);
				student.setCourse(course);
				Semester semester = semesterRepository.findById(studentDto.getSemId()).orElse(null);
				student.setSemester(semester);
				UserType studentType = userTypeRepository
						.findById(userTypeRepository.findUserTypeByName(studentDto.getType())).orElse(null);
				student.setUserType(studentType);
				return studentRepository.save(student);
			} else {
				throw new NotFoundException("Invalid Department Id or Course Id or Semester Id");
			}
		} catch (DuplicateException e) {
			logger.error(e.getMessage());
			throw new DuplicateException(e.getMessage());
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
			for (ConstraintViolation<?> violation : violations) {
				if (violation.getPropertyPath().toString().equals("email")) {
					throw new ConstraintException("Invaild email");
				} else if (violation.getPropertyPath().toString().equals("phno")) {
					throw new ConstraintException("Invaild phoneNumber");
				}
			}
			logger.error(e.getMessage());
			throw new ConstraintException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public Student getStudentById(Long studId) throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching Student by Id");
			return studentRepository.findById(studId).get();
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			throw new NotFoundException("student not found");
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void updateAvailability(Long studentId, Boolean isAvailable) throws ServiceException, NotFoundException {
		try {
			logger.info("Update Student Status Based On Availablity");
			if (Objects.nonNull(isAvailable) && Objects.nonNull(studentId)) {
				studentRepository.updateStudentStatusBasedOnAvailablity(studentId, isAvailable);
			} else {
				throw new NotFoundException("student not found");
			}
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Student updateStudentById(Long studentId, StudentDTO student)
			throws ServiceException, NotFoundException, DuplicateException, ConstraintException {
		try {
			logger.info("Update Student By Id");
			Boolean isEmailAlreadyExists = false;
			Boolean isPhoneNoAlreadyExists = false;
			Student existingStudent = studentRepository.findById(studentId).orElse(null);
			if (Objects.nonNull(existingStudent)) {
				if (!Objects.equals(student.getEmail(), existingStudent.getEmail())) {
					isEmailAlreadyExists = studentRepository.existsByEmail(student.getEmail());
				}
				if (!Objects.equals(student.getPhno(), existingStudent.getPhno())) {
					isPhoneNoAlreadyExists = studentRepository.existsByPhno(student.getPhno());
				}
				if (BooleanUtils.isTrue(isEmailAlreadyExists)) {
					throw new DuplicateException("Email is already exists.");

				}
				if (BooleanUtils.isTrue(isPhoneNoAlreadyExists)) {
					throw new DuplicateException("Phone number is already exists.");
				}
				existingStudent.setName(student.getName());
				existingStudent.setAddress(student.getAddress());
				existingStudent.setDob(student.getDob());
				existingStudent.setDateOfJoining(student.getDateOfJoining());
				existingStudent.setEmail(student.getEmail());
				existingStudent.setPhno(student.getPhno());
				if (Objects.nonNull(student.getAcademicYear())) {
					existingStudent.setAcademicYear(student.getAcademicYear());
				}
				if (Objects.nonNull(student.getDeptId())) {
					Department department = departmentRepository.findById(student.getDeptId()).orElse(null);
					existingStudent.setDepartment(department);
				}
				if (Objects.nonNull(student.getCourseId())) {
					Course course = courseRepository.findById(student.getCourseId()).orElse(null);
					existingStudent.setCourse(course);
				}
				if (Objects.nonNull(student.getSemId())) {
					Semester semester = semesterRepository.findById(student.getSemId()).orElse(null);
					existingStudent.setSemester(semester);
				}
				return studentRepository.save(existingStudent);
			} else {
				throw new NotFoundException("student not found");
			}
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (DuplicateException e) {
			logger.error(e.getMessage());
			throw new DuplicateException(e.getMessage());
		} catch (ConstraintViolationException e) {
			logger.error(e.getMessage());
			throw new ConstraintException("Check  email or phone Pattern");
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<Student> getStudentsByDeptAndCourseAndAcademicYear(FilterOptionDTO filterOption)
			throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching all students By Department and Course and Academic Year");
			if (Objects.nonNull(filterOption) && Objects.nonNull(filterOption.getDeptId())
					&& Objects.nonNull(filterOption.getCourseId()) && Objects.nonNull(filterOption.getAcademicYear())) {
				return studentRepository.getStudentByDeptAndCourseAndAcademicYear(filterOption.getDeptId(),
						filterOption.getCourseId(), filterOption.getAcademicYear());
			} else {
				throw new NotFoundException("Invalid Department Id or Course Id or Semester Id");
			}
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public boolean changePassword(ChangePaswordDTO changePaswordDto) throws NotFoundException, ServiceException {
		try {
			logger.info("Change Student Password");
			if (Objects.nonNull(changePaswordDto) && Objects.nonNull(changePaswordDto.getEmail())
					&& Objects.nonNull(changePaswordDto.getNewPassword())
					&& Objects.nonNull(changePaswordDto.getConfirmPassword())
					&& changePaswordDto.getNewPassword().equals(changePaswordDto.getConfirmPassword())) {
				studentRepository.changePassword(changePaswordDto.getEmail(), changePaswordDto.getNewPassword());
				return true;

			} else {
				throw new NotFoundException("Invalid password");
			}
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Boolean upgradeStudent(FilterOptionDTO filterOption) throws NotFoundException, ServiceException {
		try {
			logger.info("Upgrade Student");
			Boolean isEligibleForUpgrade = true;
			List<Student> students = studentRepository.getStudentByDeptAndCourseAndSemIdAndAcademicYear(
					filterOption.getAcademicYear(), filterOption.getDeptId(), filterOption.getCourseId(),
					filterOption.getSemId());// total student
			List<Subject> subjects = subjectRepository.getSubjectsByCourseAndSemId(filterOption.getCourseId(),
					filterOption.getSemId());
			List<Long> studentIds = new ArrayList<>();
			for (Student stud : students) {
				studentIds.add(stud.getId());
			}
			List<Long> subjectIds = new ArrayList<>();

			for (Subject subject : subjects) {
				subjectIds.add(subject.getId());
			}

			List<Enrollment> enrollments = enrollmentRepository.getEnrollmentDetailsByStudentIds(studentIds,
					subjectIds);
			if (CollectionUtils.isNotEmpty(enrollments)) {
				Map<Long, List<Enrollment>> enrollmentByStudentId = enrollments.stream()
						.filter(enrollment -> Objects.nonNull(enrollment.getStudent())
								&& Objects.nonNull(enrollment.getStudent().getId()))
						.collect(Collectors.groupingBy(enrollment -> enrollment.getStudent().getId()));
				if (MapUtils.isNotEmpty(enrollmentByStudentId)) {
					for (Long studentId : studentIds) {
						if (enrollmentByStudentId.containsKey(studentId)) {
							List<Enrollment> enrollmentList = enrollmentByStudentId.get(studentId);
							if (!Objects.equals(enrollmentList.size(), subjects.size())) {
								isEligibleForUpgrade = false;
								break;
							}
						} else {
							isEligibleForUpgrade = false;
							break;
						}
					}

				}
			} else {
				isEligibleForUpgrade = false;
			}
			if (BooleanUtils.isTrue(isEligibleForUpgrade)) {
				if (CollectionUtils.isNotEmpty(students) && Objects.nonNull(filterOption.getSemId())) {// all students
																										// -->
																										// enrolled-->
																										// then upgrade
					for (Student student : students) {
						Semester semester = semesterRepository.findById(student.getSemester().getId()).orElse(null);
						if (Objects.nonNull(semester)) {
							Integer order = Objects.nonNull(semester.getOrder()) ? semester.getOrder() + 1 : 0;
							Semester nextSemToUpdate = null;
							if (Objects.nonNull(semester.getCourseType())) {
								nextSemToUpdate = semesterRepository
										.getSemesterByOrderAndCourseTypeId(semester.getCourseType().getId(), order);
							}
							if (Objects.nonNull(nextSemToUpdate)) {
								student.setSemester(nextSemToUpdate);
								studentRepository.save(student);
							}
						}
					}
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
		return false;
	}

	@Override
	public List<String> getAllAcademicYear() throws ServiceException {
		try {
			logger.info("Fetching all Academic years");
			return studentRepository.getAllAcademicYear();
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Boolean findWhetherScheduleEnrolled(Long studentId) throws ServiceException {
		try {
			logger.info("Checking Whether the Enrollment Scheduled");
			Student student = studentRepository.findById(studentId).orElse(null);
			if (Objects.nonNull(student)) {
				Long enrollCount = enrollmentScheduleRepository.countEnrollmentScheduletByDeptAndCourseAndAcademicYear(
						student.getAcademicYear(), student.getDepartment().getId(), student.getCourse().getId(),
						student.getSemester().getId());
				return enrollCount > 0;

			}
			return false;
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

}
