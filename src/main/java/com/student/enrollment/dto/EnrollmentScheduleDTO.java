package com.student.enrollment.dto;

public class EnrollmentScheduleDTO {

	private Long id;
	private String academicYear;
	private Long deptId;
	private Long courseId;
	private Long semId;
	private Boolean isStarted = false;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
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
	public Boolean getIsStarted() {
		return isStarted;
	}
	public void setIsStarted(Boolean isStarted) {
		this.isStarted = isStarted;
	}
	
	
}
