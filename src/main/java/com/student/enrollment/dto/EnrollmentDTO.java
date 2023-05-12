package com.student.enrollment.dto;

import java.time.LocalDate;

public class EnrollmentDTO {
	private String studentName;
	private String staffName;
	private String subjectName;
	private Long staffId;
	private Long subjectId;
	private Long studentId;
	private Long deptId;
	private Long courseId;
	private Long semId;
	private String subjectCode;
	private String academicYear;
	private String email;
	private String address;
	private String phno;
	private LocalDate dob;
	private LocalDate dateOfJoining;
	
	
	public EnrollmentDTO( String subjectName,String subjectCode,String staffName) {
		this.subjectName = subjectName;
		this.subjectCode = subjectCode;
		this.staffName = staffName;
	}

	

	public EnrollmentDTO(String studentName, String email, String address, String phno, LocalDate dob,
			LocalDate dateOfJoining) {
		super();
		this.studentName = studentName;
		this.email = email;
		this.address = address;
		this.phno = phno;
		this.dob = dob;
		this.dateOfJoining = dateOfJoining;
	}



	public EnrollmentDTO() {
		super();
	}

	public EnrollmentDTO(String studentName, String staffName, String subjectName, Long staffId, Long subjectId,
			Long deptId, Long courseId, Long semId) {

		this.studentName = studentName;
		this.staffName = staffName;
		this.subjectName = subjectName;
		this.staffId = staffId;
		this.subjectId = subjectId;
		this.deptId = deptId;
		this.courseId = courseId;
		this.semId = semId;
	}

	public EnrollmentDTO(String studentName, String staffName, String subjectName, Long staffId, Long subjectId,
			Long deptId) {
		super();
		this.studentName = studentName;
		this.staffName = staffName;
		this.subjectName = subjectName;
		this.staffId = staffId;
		this.subjectId = subjectId;
		this.deptId = deptId;
	}

	public EnrollmentDTO(String subjectName, String staffName) {
		super();
		this.subjectName = subjectName;
		this.staffName = staffName;

	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getSemId() {
		return semId;
	}

	public void setSemId(Long semId) {
		this.semId = semId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}


	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhno() {
		return phno;
	}


	public void setPhno(String phno) {
		this.phno = phno;
	}



	public LocalDate getDob() {
		return dob;
	}



	public void setDob(LocalDate dob) {
		this.dob = dob;
	}



	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}



	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

 
	
	

}
