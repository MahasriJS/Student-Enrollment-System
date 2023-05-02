package com.student.enrollment.repositorty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.enrollment.dto.EnrollmentDTO;
import com.student.enrollment.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	@Query("SELECT new com.student.enrollment.dto.EnrollmentDTO(e.student.name, e.staff.name, e.subject.name) FROM Enrollment e WHERE e.id = :enrollmentId")
	EnrollmentDTO getEnrollmentDetails(@Param("enrollmentId") Long enrollmentId);

	@Query("SELECT COUNT(e) FROM Enrollment e WHERE e.subject.id = :subjectId AND e.student.id = :studentId")
	Long existingEnrollments(@Param("subjectId") Long subjectId, @Param("studentId") Long studentId);

	@Query("SELECT COUNT(e) FROM Enrollment e where e.subject.id=:subjectId and e.staff.id=:staffId")
	Long countEnrollmentBySubjectAndStaffById(@Param("subjectId") Long subjectId, @Param("staffId") Long staffId);

	@Query("SELECT new com.student.enrollment.dto.EnrollmentDTO(e.student.name) FROM Enrollment e WHERE e.student.department.id=:deptId and e.student.course.id=:courseId and e.student.semester.id=:semId and e.subject.id=:subjectId  and e.staff.id=:staffId")
	List<EnrollmentDTO> getEnrollmentDetailsByAdmin(@Param("deptId") Long deptId, @Param("courseId") Long courseId,
			@Param("semId") Long semesterId, @Param("subjectId") Long subjectId, @Param("staffId") Long staffId);

	@Query("SELECT new com.student.enrollment.dto.EnrollmentDTO(e.subject.name, e.subject.code,e.staff.name) FROM Enrollment e WHERE e.student.semester.id=:semId AND e.student.id=:studentId")
	List<EnrollmentDTO> getEnrollmentDetailsByStudent(@Param("semId") Long semId,@Param("studentId") Long studentId);

}
