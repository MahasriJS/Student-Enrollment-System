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
	void updateStudentStatusBasedOnAvailablity(@Param("id") Long id, @Param("isAvailable") boolean newAvailability);

	@Query("FROM Student s where s.academicYear=:academicYear and s.department.id=:deptId and s.course.id=:courseId and s.semester.id=:semId")
	List<Student> getStudentByDeptAndCourseAndSemIdAndAcademicYear(@Param("academicYear") String academicYear,
			@Param("deptId") Long deptId, @Param("courseId") Long courseId, @Param("semId") Long semId);

	@Query("FROM Student s where s.department.id=:deptId and s.course.id=:courseId and s.academicYear=:academicYear")
	List<Student> getStudentByDeptAndCourseAndAcademicYear(@Param("deptId") Long deptId,
			@Param("courseId") Long courseId, @Param("academicYear") String academicYear);

	@Query("SELECT COUNT(s) FROM Student s WHERE s.department.id = :deptId AND s.course.id = :courseId AND s.semester.id = :semId AND s.isAvailable=true")
	Long countStudentsByDepartmentCourseAndSemesterId(@Param("deptId") Long deptId, @Param("courseId") Long courseId,
			@Param("semId") Long semId);

	@Query("SELECT s.id FROM Student s where s.email=:email AND s.password=:password AND s.userType.type=:userType")
	Long getStudentByEmailAndPassword(@Param("email") String email, @Param("password") String password,
			@Param("userType") String userType);

	@Transactional
	@Modifying
	@Query("UPDATE Student	s SET s.password = :newPassword WHERE s.email = :email")
	void changePassword(@Param("email") String email, @Param("newPassword") String newPassword);

	@Transactional
	@Modifying
	@Query("UPDATE Student s SET s.semester.id = :semId Where s.id=:studentId")
	void upgradeStudentOrder(@Param("semId") Long semId, @Param("studentId") Long studentId);

	@Query("SELECT distinct academicYear FROM Student")
	List<String> getAllAcademicYear();

}