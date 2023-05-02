package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.NoSuchElementException;

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

	public List<Department> getAllDepartments() throws ServiceException {
		try {
			return deptRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Department getDepartmentById(Long deptId) throws ServiceException, NotFoundException {
		try {
			return deptRepository.findById(deptId).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Department not found");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

	}
}
