package com.student.enrollment.repositorty;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.enrollment.entity.EnrollmentSchedule;



	@Repository
	public interface EnrollmentScheduleRepository  extends JpaRepository<EnrollmentSchedule, Long> {	
		@Query("SELECT COUNT(s) FROM  EnrollmentSchedule s where s.academicYear=:academicYear and s.department.id=:deptId and s.course.id=:courseId and s.semester.id=:semId")
		Long countEnrollmentScheduletByDeptAndCourseAndAcademicYear(@Param("academicYear") String academicYear,@Param("deptId") Long deptId, @Param("courseId") Long courseId,
				@Param("semId") Long semId);
		

}
