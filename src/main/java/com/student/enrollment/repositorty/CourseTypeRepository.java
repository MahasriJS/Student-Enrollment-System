package com.student.enrollment.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.student.enrollment.entity.CourseType;

@Repository
public interface CourseTypeRepository extends JpaRepository<CourseType,Long>{
}
