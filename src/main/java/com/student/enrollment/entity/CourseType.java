package com.student.enrollment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "course_type")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class CourseType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String courseTypeName;
	@Column(name = "duration", nullable = false)
	private Integer durationInYears;

	public Integer getDurationInYears() {
		return durationInYears;
	}

	public void setDurationInYears(Integer duration) {
		this.durationInYears = duration;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseTypeName() {
		return courseTypeName;
	}

	public void setCourseTypeName(String courseName) {
		this.courseTypeName = courseName;
	}

}
