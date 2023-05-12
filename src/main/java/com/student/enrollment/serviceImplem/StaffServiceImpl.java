package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.student.enrollment.dto.StaffDTO;
import com.student.enrollment.dto.StaffSubjectAssignDTO;
import com.student.enrollment.entity.Department;
import com.student.enrollment.entity.Staff;
import com.student.enrollment.entity.Subject;
import com.student.enrollment.entity.UserType;
import com.student.enrollment.entity.StaffSubjectAssign;
import com.student.enrollment.exception.ConstraintException;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.mapping.StaffMapper;
import com.student.enrollment.mapping.StaffSubjectAssignMapper;
import com.student.enrollment.repositorty.DepartmentRepository;
import com.student.enrollment.repositorty.StaffRepository;
import com.student.enrollment.repositorty.StaffSubjectAssignRepository;
import com.student.enrollment.repositorty.SubjectRepository;
import com.student.enrollment.repositorty.UserTypeRepository;
import com.student.enrollment.service.StaffService;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private StaffSubjectAssignRepository staffSubjectAssignRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private UserTypeRepository userTypeRepository;

	Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

	public Staff addStaff(StaffDTO staffDto)
			throws ServiceException, DuplicateException, NotFoundException, ConstraintException {
		try {
			logger.info("Adding Staff");
			Staff staff = StaffMapper.dtoToEntity(staffDto);
			if (BooleanUtils.isTrue(staffRepository.existsByEmail(staff.getEmail()))) {
				throw new DuplicateException("Email address is already exists.");
			}
			if (BooleanUtils.isTrue(staffRepository.existsByPhno(staff.getPhno()))) {
				throw new DuplicateException("Phone Number already exists.");
			}

			if (Objects.nonNull(staffDto) && Objects.nonNull(staffDto.getDeptId())) {
				staff.setPassword(staffDto.getDob().toString());
				Department department = departmentRepository.findById(staffDto.getDeptId()).orElse(null);
				staff.setDepartment(department);
				UserType staffType = userTypeRepository
						.findById(userTypeRepository.findUserTypeByName(staffDto.getType())).orElse(null);
				staff.setUserType(staffType);
				return staffRepository.save(staff);
			} else {
				throw new NotFoundException("Invalid Department Id or User Type Id");
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
			throw new ServiceException(" Failed to add Staff");
		}
	}

	public List<Staff> getStaffsByDeptId(List<Long> deptIds) throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching Staffs By Department Id");
			if (CollectionUtils.isNotEmpty(deptIds)) {
				return staffRepository.getStaffsByDeptId(deptIds);

			} else {
				throw new NotFoundException("Invalid Department Id");
			}
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Staff> getStaffsBySubjectId(Long subjectId) throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching Staff by Subject Id");
			if (Objects.nonNull(subjectId)) {
				return staffRepository.getStaffsBySubjectId(subjectId);
			} else {
				throw new NotFoundException("Invalid Subject Id");
			}
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	public StaffSubjectAssign saveStaffAssignment(StaffSubjectAssignDTO staffSubjectAssignDto, Long subjectId)
			throws ServiceException, NotFoundException {
		try {
			logger.info("Assign Staff to the Subject");
			StaffSubjectAssign staffSubjectAssign = StaffSubjectAssignMapper.dtoToEntity(staffSubjectAssignDto);
			Long assignedStaffCount = staffSubjectAssignRepository
					.countAssignedStafftoSubject(staffSubjectAssignDto.getStaffId(), subjectId);
			if (Objects.nonNull(staffSubjectAssignDto) && Objects.nonNull(staffSubjectAssignDto.getStaffId())
					&& Objects.nonNull(subjectId) && subjectId >= 1 && staffSubjectAssignDto.getStaffId() >= 1) {
				if (assignedStaffCount < 1) {
					Staff staff = staffRepository.findById(staffSubjectAssignDto.getStaffId()).orElse(null);
					staffSubjectAssign.setStaff(staff);
					Subject subject = subjectRepository.findById(subjectId).orElse(null);
					staffSubjectAssign.setSubject(subject);
					return staffSubjectAssignRepository.save(staffSubjectAssign);
				} else {
					throw new NotFoundException("Unable to assign staff");
				}
			} else {
				throw new NotFoundException("Invalid Staff Id or Subject Id");
			}

		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	public Staff getStaffById(Long staffId) throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching Staff By Id");
			return staffRepository.findById(staffId).get();
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			throw new NotFoundException("staff not found");
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	public void updateAvailability(Long staffId, Boolean isAvailable) throws ServiceException, NotFoundException {
		try {
			logger.info("Update Staff Status Based on Availabilty");
			if (Objects.nonNull(isAvailable) && Objects.nonNull(staffId)) {
				staffRepository.updateStaffStatusBasedOnAvailablity(staffId, isAvailable);
			} else {
				throw new NotFoundException("staff not found");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	public Staff updateStaffById(Long staffId, StaffDTO staff)
			throws ServiceException, NotFoundException, DuplicateException, ConstraintException {
		try {
			logger.info("Update Staff Details By Id");
			Staff existingStaff = staffRepository.findById(staffId).get();
			Staff newStudentByEmail = staffRepository.findExistingStaffByEmail(staff.getEmail(), existingStaff.getId());
			Staff newStudentByPhno = staffRepository.findExistingStaffByPhno(staff.getPhno(), existingStaff.getId());
			if (Objects.nonNull(newStudentByEmail)) {
				throw new DuplicateException("Email is already exists.");
			}
			if (Objects.nonNull(newStudentByPhno)) {
				throw new DuplicateException("Phno is already exists.");
			}
			if (Objects.nonNull(existingStaff)) {
				existingStaff.setName(staff.getName());
				existingStaff.setAddress(staff.getAddress());
				existingStaff.setDob(staff.getDob());
				existingStaff.setDateOfJoining(staff.getDateOfJoining());
				existingStaff.setDesignation(staff.getDesignation());
				existingStaff.setEmail(staff.getEmail());
				existingStaff.setPhno(staff.getPhno());
				existingStaff.setSalary(staff.getSalary());

				return staffRepository.save(existingStaff);
			} else {
				throw new NotFoundException("staff not found");
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
			throw new ServiceException(" Failed to update Staff");
		}
	}

}
