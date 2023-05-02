package com.student.enrollment.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.enrollment.entity.UserType;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

	 @Query("SELECT ut.id FROM UserType ut WHERE ut.type = 'student'")
	 Long findStudentUserTypeByName();
	 @Query("SELECT ut.id FROM UserType ut WHERE ut.type = 'staff'")
	 Long findStaffUserTypeByName();


}
