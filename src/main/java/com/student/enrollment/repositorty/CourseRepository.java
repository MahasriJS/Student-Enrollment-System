package com.student.enrollment.repositorty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.enrollment.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	@Query("FROM Course c where c.department.id=:deptId and c.courseType.id=:courseTypeId")
	List<Course> getCoursesByDeptAndCoursTypeId(@Param("deptId") Long deptId, @Param("courseTypeId") Long courseTypeId);

}
