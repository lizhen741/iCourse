package com.iCourse.course.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.HomeworkIssued;

public interface HomeworkIssuedService {

	Page<HomeworkIssued> teacherGetHomework(Specification<HomeworkIssued> spec, Pageable pageable);

	void save(HomeworkIssued homeworkIssued, List<Clazz> clazzes);

	HomeworkIssued findById(Long homeworkIssued_id);

}
