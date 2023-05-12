package com.student.enrollment.repositorty;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.enrollment.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

	boolean existsByEmail(String email);

	boolean existsByPhno(String phno);

	@Query("FROM Staff s where s.department.id in (:deptIds)")
	List<Staff> getStaffsByDeptId(List<Long> deptIds);

	@Query("SELECT s FROM Staff s JOIN  s.teachings t WHERE t.subject.id = :subjectId")
	List<Staff> getStaffsBySubjectId(@Param("subjectId") Long subjectId);

	@Query("SELECT COUNT(s) FROM Staff s JOIN s.teachings t WHERE s.department.id = :deptId AND t.subject.id = :subjectId  AND s.isAvailable=true")
	Long countStaffByDepartmentAndSubjectId(@Param("deptId") Long deptId, @Param("subjectId") Long subjectId);

	@Transactional
	@Modifying
	@Query("UPDATE Staff s SET s.isAvailable = :isAvailable WHERE s.id = :id")
	void updateStaffStatusBasedOnAvailablity(@Param("id") Long id, @Param("isAvailable") boolean isAvailable);

	@Query("SELECT s.id FROM Staff s where s.email=:email and s.password=:password and s.userType.type=:userType")
	Long getStaffByEmailAndPassword(@Param("email") String email, @Param("password") String password,
			@Param("userType") String userType);

	@Query("FROM Staff s where s.email=:email and s.id!=:staffId")
	Staff findExistingStaffByEmail(@Param("email") String email, @Param("staffId") Long staffId);

	@Query("FROM Staff s where s.phno=:phno and s.id!=:staffId")
	Staff findExistingStaffByPhno(@Param("phno") String phno, @Param("staffId") Long staffId);
}
