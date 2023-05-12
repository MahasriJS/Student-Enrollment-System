package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.entity.Department;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.repositorty.DepartmentRepository;
import com.student.enrollment.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository deptRepository;
	Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	@Override
	public List<Department> getAllDepartments() throws ServiceException {
		try {
			logger.info("Fetching all Departments");
			return deptRepository.findAll();
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Department getDepartmentById(Long deptId) throws ServiceException, NotFoundException {
		try {
			logger.info("Fetching all Department By Id");
			return deptRepository.findById(deptId).get();
		} catch (NoSuchElementException e) {
			logger.error("Department not found");
			throw new NotFoundException("Department not found");
		} catch (Exception e) {
			logger.error("Service Exception");
			throw new ServiceException(e.getMessage());
		}

	}
}
