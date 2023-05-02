package com.student.enrollment.repositorty;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.enrollment.entity.Staff;
import com.student.enrollment.entity.Student;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
	
	boolean existsByEmail(String email);
	
	boolean existsByPhno(String phno);
	
	@Query("FROM Staff s where s.department.id=:deptId")
	List<Staff> getStaffByDeptId(@Param("deptId") Long deptId);

	@Query("SELECT s FROM Staff s JOIN  s.teachings t WHERE t.subject.id = :subjectId")
	List<Staff> getStaffBySubjectId(@Param("subjectId") Long subjectId);

	@Query("SELECT COUNT(s) FROM Staff s JOIN s.teachings t WHERE s.department.id = :deptId AND t.subject.id = :subjectId")
	Long countStaffByDepartmentAndSubjectId(@Param("deptId") Long deptId, @Param("subjectId") Long subjectId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Staff s SET s.isAvailable = :isAvailable WHERE s.id = :id")
	void updateStaffBasedOnAvailablity(@Param("id") Long id, @Param("isAvailable") boolean isAvailable);
	
	@Query("FROM Staff s where s.email=:email and s.password=:password")
	Staff getStaffByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
