package com.iCourse.course.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;

public interface HomeworkService {

	void save(Homework homework);

	Page<Homework> getHomework(Specification<Homework> spec, Pageable pageable);

	List<Exercises> getExercisesByHomework_id(Long homework_id);

	Homework getHomework(Long homework_id);

}
