package com.student.enrollment.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.enrollment.entity.StaffSubjectAssign;

@Repository
public interface StaffSubjectAssignRepository extends JpaRepository<StaffSubjectAssign, Long> {
	@Query("SELECT COUNT(s) FROM StaffSubjectAssign s WHERE s.staff.id = :staffId AND s.subject.id = :subjectId")
	Long countAssignedStafftoSubject(@Param("staffId") Long staffId, @Param("subjectId") Long subjectId);
}
