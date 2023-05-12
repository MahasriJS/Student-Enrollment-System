package com.student.enrollment.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.enrollment.entity.Department;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
