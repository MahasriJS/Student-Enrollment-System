package com.student.enrollment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="user_type")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class UserType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name="type",nullable=true,unique=true)
	private String type;
	public UserType(Long userTypeId) {
		
	}
	public UserType() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsers() {
		return type;
	}
	public void setUsers(String users) {
		this.type = users;
	}

	

}
