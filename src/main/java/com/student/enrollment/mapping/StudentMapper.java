package com.student.enrollment.mapping;


import com.student.enrollment.dto.StudentDTO;
import com.student.enrollment.entity.Student;


public class StudentMapper {
	public static Student dtoToEntity(StudentDTO studentDto) {
		Student student= new Student();
		student.setId(studentDto.getId());
		student.setName(studentDto.getName());
		student.setDob(studentDto.getDob());
		student.setDateOfJoining(studentDto.getDateOfJoining());
		student.setAddress(studentDto.getAddress());
		student.setAcademicYear(studentDto.getAcademicYear());
		student.setEmail(studentDto.getEmail());
		student.setPhno(studentDto.getPhno());
		student.setPassword(studentDto.getPassword());
		return student;
	}

}
