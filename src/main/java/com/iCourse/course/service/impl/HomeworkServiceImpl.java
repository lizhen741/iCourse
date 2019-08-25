package com.iCourse.course.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.dao.HomeworkDao;
import com.iCourse.course.service.HomeworkService;

@Service
@Transactional
public class HomeworkServiceImpl implements HomeworkService{
	@Autowired
	private HomeworkDao homeworkDao;

	@Override
	public void save(Homework homework) {
		homeworkDao.save(homework);
	}

	@Override
	public Page<Homework> getHomework(Specification<Homework> spec, Pageable pageable) {
		return homeworkDao.findAll(spec, pageable);
	}

	@Override
	public List<Exercises> getExercisesByHomework_id(Long homework_id) {
		Homework homework = homeworkDao.findById(homework_id).get();
		List<Exercises> exercisesList = homework.getExercisesList();
		for (Exercises exercises : exercisesList) {
			break;
		}
		return exercisesList;
	}

	@Override
	public Homework getHomework(Long homework_id) {
		return homeworkDao.findById(homework_id).get();
	}
	
	
}
