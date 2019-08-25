package com.iCourse.course.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.ExercisesItem;
import com.iCourse.course.dao.ExercisesDao;
import com.iCourse.course.dao.ExercisesItemDao;
import com.iCourse.course.service.ExercisesService;
@Service
@Transactional
public class ExercisesServiceImpl implements ExercisesService{

	@Autowired
	private ExercisesDao exercisesDao;
	@Autowired
	private ExercisesItemDao exercisesItemDao;
	@Override
	public Page<Exercises> getExercises(Specification<Exercises> spec, Pageable pageable) {
		return exercisesDao.findAll(spec, pageable);
	}
	@Override
	public void save(Exercises exercises) {
		List<ExercisesItem> exercisesItems = exercises.getExercisesItems();
		exercisesDao.save(exercises);
		for (ExercisesItem exercisesItem : exercisesItems) {
			exercisesItem.setExercises(exercises);
			exercisesItemDao.save(exercisesItem);
		}
	}
	@Override
	public List<Exercises> getExercisesByChapters(Specification<Exercises> spec) {
		return exercisesDao.findAll(spec);
	}
	
	
	
	
}
