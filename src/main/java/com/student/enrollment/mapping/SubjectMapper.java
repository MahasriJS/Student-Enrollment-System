package com.student.enrollment.mapping;

import com.student.enrollment.dto.SubjectDTO;
import com.student.enrollment.entity.Subject;

public class SubjectMapper {
	public static Subject DtoToEntity(SubjectDTO subjectDto) {
		Subject subject = new Subject();
		subject.setId(subjectDto.getId());
		subject.setCode(subjectDto.getCode());
		subject.setName(subjectDto.getName());
		subject.setCredits(subjectDto.getCredits());
		return subject;
	}
}
