package com.student.enrollment.dto;

public class FilterOptionDTO {
	private Long staffId;
	private Long courseId;
	private Long semId;
	private Long deptId;
	private Long subjectId;
	private Long studentId;
	private Long courseTypeId;
	private Integer order;
	private String email;
	private String academicYear;
	
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

	public Long getCourseTypeId() {
		return courseTypeId;
	}

	public void setCourseTypeId(Long courseTypeId) {
		this.courseTypeId = courseTypeId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studId) {
		this.studentId = studId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
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

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	

}
