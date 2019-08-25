package com.iCourse.exercise;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.iCourse.BaseTest;
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.dao.ExercisesDao;

public class FindTest extends BaseTest{

	@Autowired
	private ExercisesDao edao;
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void find() {
		Sort sort = Sort.by(Sort.Direction.ASC, "exercises_type");
		List<Exercises> findAll = (List<Exercises>) edao.findAll(sort);
		System.out.println();
		
	}
}
