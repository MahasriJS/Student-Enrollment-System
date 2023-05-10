package com.student.enrollment.service;

import com.student.enrollment.dto.LoginDTO;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;

public interface LoginService {
	/**
	 * 
	 * @param loginDto
	 * @param userType
	 * @return {@link Long}
	 * @throws ServiceException
	 * @throws NotFoundException
	 */

	public Long login(LoginDTO loginDto, String userType) throws ServiceException, NotFoundException;
}
