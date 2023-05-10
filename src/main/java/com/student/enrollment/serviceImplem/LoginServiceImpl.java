package com.student.enrollment.serviceImplem;

import java.util.Objects;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.dto.LoginDTO;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.repositorty.StaffRepository;
import com.student.enrollment.repositorty.StudentRepository;
import com.student.enrollment.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StaffRepository staffRepository;

	Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	public Long login(LoginDTO loginDto, String userType) throws ServiceException, NotFoundException {
		try {
			logger.info(userType + "Login");
			if (Objects.nonNull(loginDto) && Objects.nonNull(loginDto.getEmail())
					&& Objects.nonNull(loginDto.getPassword()) && Objects.nonNull(userType)) {

				if (Objects.equals(userType, "Student")) {
					if (BooleanUtils.isNotTrue(studentRepository.existsByEmail(loginDto.getEmail()))) {
						throw new NotFoundException("Email Not Found");
					}
					Long studentId = studentRepository.getStudentByEmailAndPassword(loginDto.getEmail(),
							loginDto.getPassword(), userType);
					if (Objects.nonNull(studentId)) {
						return studentId;
					} else {
						throw new NotFoundException("student not found");
					}
				} else if (Objects.equals(userType, "Admin")) {
					if (BooleanUtils.isNotTrue(staffRepository.existsByEmail(loginDto.getEmail()))) {
						throw new NotFoundException("Email Not Found");
					}
					Long staffId = staffRepository.getStaffByEmailAndPassword(loginDto.getEmail(),
							loginDto.getPassword(), userType);
					if (Objects.nonNull(staffId)) {
						return staffId;
					} else {
						throw new NotFoundException("staff not found");
					}
				}

			} else {
				throw new NotFoundException("Invalid username or password");
			}
		} catch (NotFoundException e) {
			logger.error("Invalid username or password");
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}

		return null;
	}

}
