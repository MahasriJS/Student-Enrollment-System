package com.student.enrollment.repositorty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.enrollment.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	boolean existsByCode(String code);
	
	@Query("FROM Subject s where s.course.id=:courseId and s.semester.id=:semId")
	List<Subject> getSubjectsByCourseAndSemId(@Param("courseId") Long courseId, @Param("semId") Long semId);

}
