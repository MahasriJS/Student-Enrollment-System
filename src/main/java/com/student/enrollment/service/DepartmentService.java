package com.student.enrollment.service;

import java.util.List;
import com.student.enrollment.entity.Department;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface DepartmentService {
	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Department> getAllDepartments() throws ServiceException;

	/**
	 * 
	 * @param deptId
	 * @return
	 * @throws ServiceException
	 * @throws NotFoundException
	 */
	public Department getDepartmentById(Long deptId) throws ServiceException, NotFoundException;
}
