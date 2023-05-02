package com.student.enrollment.serviceImplem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.enrollment.dto.FilterOptionDTO;
import com.student.enrollment.dto.SubjectDTO;
import com.student.enrollment.entity.Course;
import com.student.enrollment.entity.Semester;
import com.student.enrollment.entity.Subject;
import com.student.enrollment.exception.DuplicateException;
import com.student.enrollment.exception.NotFoundException;
import com.student.enrollment.exception.ServiceException;
import com.student.enrollment.mapping.SubjectMapper;
import com.student.enrollment.repositorty.CourseRepository;
import com.student.enrollment.repositorty.SemesterRepository;
import com.student.enrollment.repositorty.SubjectRepository;
import com.student.enrollment.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private SemesterRepository semesterRepository;

	@Override
	public List<Subject> getSubjectsByCourseAndSemId(FilterOptionDTO filterOption) throws ServiceException, NotFoundException {
		try {
			if (Objects.nonNull(filterOption) && Objects.nonNull(filterOption.getCourseId())
					&& Objects.nonNull(filterOption.getSemId())) {
				List<Subject> subjects = subjectRepository.getSubjectsByCourseAndSemId(filterOption.getCourseId(),
						filterOption.getSemId());
				return subjects;
			} else {
				throw new NotFoundException("Invalid Course Id or Semester Id");
			}
		}catch(NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Subject addSubject(SubjectDTO subjectDto) throws ServiceException,NotFoundException, DuplicateException {
		try {
			Subject subject = SubjectMapper.DtoToEntity(subjectDto);
			String code = subjectDto.getCode();
			if (subjectRepository.existsByCode(code) == true) {
				throw new DuplicateException("Code is already exists.");
			}
			if (Objects.nonNull(subjectDto) && Objects.nonNull(subjectDto.getCourseId())
					&& Objects.nonNull(subjectDto.getSemesterId())) {
			Course course = courseRepository.findById(subjectDto.getCourseId()).orElse(null);
			subject.setCourse(course);
			Semester sem = semesterRepository.findById(subjectDto.getSemesterId()).orElse(null);
			subject.setSemester(sem);
			return subjectRepository.save(subject);
			}else {
				throw new NotFoundException("Invalid Course Id or Semester Id");
			}
		}catch(DuplicateException e) {
			throw new DuplicateException(e.getMessage());
		}catch(NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Subject getSubjectById(Long subjectId) throws ServiceException, NotFoundException {
		try {
			return subjectRepository.findById(subjectId).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("subject not found");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
