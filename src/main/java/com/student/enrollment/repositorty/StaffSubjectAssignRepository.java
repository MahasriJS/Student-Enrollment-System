package com.student.enrollment.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.enrollment.entity.StaffSubjectAssign;

@Repository
public interface StaffSubjectAssignRepository extends JpaRepository<StaffSubjectAssign,Long>{



}
