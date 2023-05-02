package com.student.enrollment.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "staff", uniqueConstraints = { @UniqueConstraint(columnNames = { "phno", "email" }) })
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 45)
	private String name;
	@Column(nullable = false)
	private LocalDate dob;
	@Column(nullable = false)
	private LocalDate dateOfJoining;
	@Column(nullable = false, length = 12)
	@Pattern(regexp = "^[0-9]{10,12}$")
	@Valid
	private String phno;
	@Column(nullable = false, length = 45)
	@Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
	@Valid
	private String email;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private Double salary;
	@Column(nullable = false)
	private String designation;
	@Column(nullable = false)
	private Boolean isAvailable = true;
	@Column(nullable = false)
	private String password="temp";

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_type_id", nullable = false)
	private UserType userType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;

	@JsonIgnore
	@OneToMany(mappedBy = "staff")
	private List<StaffSubjectAssign> teachings;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<StaffSubjectAssign> getTeachings() {
		return teachings;
	}

	public void setTeachings(List<StaffSubjectAssign> teachings) {
		this.teachings = teachings;
	}

}