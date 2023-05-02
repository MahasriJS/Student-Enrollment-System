package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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

	public Staff addStaff(StaffDTO staffDto) throws ServiceException, DuplicateException, NotFoundException {
		try {
			Staff staff = StaffMapper.dtoToEntity(staffDto);
			String email = staff.getEmail();
			String phno = staff.getPhno();
			if (staffRepository.existsByEmail(email) == true) {
				throw new DuplicateException("Email address is already exists.");
			}
			if (staffRepository.existsByPhno(phno) == true) {
				throw new DuplicateException("Phone Number is already exists.");
			}
			if (Objects.nonNull(staffDto) && Objects.nonNull(staffDto.getDeptId())) {
				Department department = departmentRepository.findById(staffDto.getDeptId()).orElse(null);
				staff.setDepartment(department);
				UserType staffType = userTypeRepository.findById(userTypeRepository.findStaffUserTypeByName()).orElse(null);
				 staff.setUserType(staffType);
				return staffRepository.save(staff);
			} else {
				throw new NotFoundException("Invalid Department Id or User Type Id");
			}
		} catch (DuplicateException e) {
			throw new DuplicateException(e.getMessage());
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(" Failed to add Staff");
		}
	}

	public List<Staff> getStaffsByDeptId(Long deptId) throws ServiceException, NotFoundException {
		try {
			if (Objects.nonNull(deptId)) {
				return staffRepository.getStaffByDeptId(deptId);

			} else {
				throw new NotFoundException("Invalid Department Id");
			}
		} catch (NoSuchElementException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Staff> getStaffsBySubjectId(Long subjectId) throws ServiceException, NotFoundException {
		try {
			if (Objects.nonNull(subjectId)) {
				return staffRepository.getStaffBySubjectId(subjectId);
			} else {
				throw new NotFoundException("Invalid Subject Id");
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public StaffSubjectAssign assignStaffToSubject(StaffSubjectAssignDTO staffSubjectAssignDto)
			throws ServiceException, NotFoundException {
		try {
			StaffSubjectAssign staffSubjectAssign = StaffSubjectAssignMapper.dtoToEntity(staffSubjectAssignDto);
			if (Objects.nonNull(staffSubjectAssignDto) && Objects.nonNull(staffSubjectAssignDto.getStaffId())
					&& Objects.nonNull(staffSubjectAssignDto.getSubjectId())) {
				Staff staff = staffRepository.findById(staffSubjectAssignDto.getStaffId()).orElse(null);
				staffSubjectAssign.setStaff(staff);
				Subject subject = subjectRepository.findById(staffSubjectAssignDto.getSubjectId()).orElse(null);
				staffSubjectAssign.setSubject(subject);
				return staffSubjectAssignRepository.save(staffSubjectAssign);
			} else {
				throw new NotFoundException("Invalid Staff Id or Subject Id");
			}

		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public Staff getStaffById(Long staffId) throws ServiceException, NotFoundException {
		try {
			return staffRepository.findById(staffId).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("staff not found");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void updateAvailability(Long id, boolean isAvailable) throws ServiceException, NotFoundException {
		try {
			
			staffRepository.updateStaffBasedOnAvailablity(id,isAvailable);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException("staff not found");
		} catch (Exception e) {
			throw new ServiceException("Staff Not Found");
		}
	}

	public Staff updateStaffById(Long id, Staff staff) throws ServiceException, NotFoundException, DuplicateException {
		try {
			Staff existingStaff = staffRepository.findById(id).get();
			existingStaff.setName(staff.getName());
			existingStaff.setAddress(staff.getAddress());
			existingStaff.setDob(staff.getDob());
			existingStaff.setDateOfJoining(staff.getDateOfJoining());
			existingStaff.setDesignation(staff.getDesignation());
			existingStaff.setEmail(staff.getEmail());
			existingStaff.setPhno(staff.getPhno());
			existingStaff.setSalary(staff.getSalary());
			existingStaff.setIsAvailable(staff.getIsAvailable());
			return staffRepository.save(existingStaff);

		} catch (NoSuchElementException e) {
			throw new NotFoundException("staff not found");
		} catch (Exception e) {
			throw new ServiceException(" Failed to update Staff");

		}
	}

	@Override
	public Staff staffLogin(StaffDTO staffDto) throws ServiceException, NotFoundException {
		try {
			String email = staffDto.getEmail();
			if (Objects.nonNull(staffDto) && Objects.nonNull(staffDto.getEmail())
					&& Objects.nonNull(staffDto.getPassword()) && staffRepository.existsByEmail(email) == true) {
				return staffRepository.getStaffByEmailAndPassword(email, staffDto.getPassword());
			} else {
				throw new NotFoundException("Invalid username or password");
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
