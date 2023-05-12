package com.student.enrollment.repositorty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.enrollment.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

	@Query("SELECT s FROM Semester s WHERE s.courseType.id = :courseTypeId")
	List<Semester> findSemestersByCourseTypeId(@Param("courseTypeId") Long courseTypeId);

	@Query("From Semester s where s.courseType.id=:courseTypeId and s.order=:order")
	Semester getSemesterByOrderAndCourseTypeId(@Param("courseTypeId") Long courseTypeId, @Param("order") Integer order);
}
