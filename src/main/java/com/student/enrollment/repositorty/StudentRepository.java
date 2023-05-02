package com.student.enrollment.repositorty;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.enrollment.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	boolean existsByEmail(String email);

	boolean existsByPhno(String phno);
	
	boolean existsByPassword(String password);
	
	@Transactional
	@Modifying
	@Query("UPDATE Student s SET s.isAvailable = :isAvailable WHERE s.id = :id")
	void updateStudentBasedOnAvailablity(@Param("id") Long id, @Param("isAvailable") boolean newAvailability);
	
	@Query("FROM Student s where s.department.id=:deptId and s.course.id=:courseId and s.semester.id=:semId")
	List<Student> getStudentByDeptAndCourseAndSemId(@Param("deptId") Long deptId, @Param("courseId") Long courseId,
			@Param("semId") Long semId);

	@Query("SELECT COUNT(s) FROM Student s WHERE s.department.id = :deptId AND s.course.id = :courseId AND s.semester.id = :semId AND s.isAvailable=true")
	Long countStudentsByDepartmentCourseAndSemesterId(@Param("deptId") Long deptId, @Param("courseId") Long courseId,
			@Param("semId") Long semId);
	
	@Query("FROM Student s where s.email=:email and s.password=:password")
	Student getStudentByEmailAndPassword(@Param("email") String email, @Param("password") String password);
	
	 @Transactional
	 @Modifying
	 @Query("UPDATE Student	s SET s.password = :newPassword WHERE s.email = :email")
	 void changePassword(@Param("email") String email, @Param("newPassword") String newPassword);



	
}