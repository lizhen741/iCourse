package com.iCourse.course.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.course.bean.Exercises;

public interface ExercisesService {

	Page<Exercises> getExercises(Specification<Exercises> spec, Pageable pageable);

	void save(Exercises exercises);

	List<Exercises> getExercisesByChapters(Specification<Exercises> spec);

}
