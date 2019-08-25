package com.iCourse.homework;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.iCourse.BaseTest;
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.bean.StudentHomework;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.StudentHomeworkDao;
import com.iCourse.course.service.StudentHomeworkService;

public class FindTest extends BaseTest{
	@Autowired
	private ClazzDao cdao;
	@Autowired
	private StudentHomeworkDao shdao;
	@Autowired
	private StudentHomeworkService shService;
	
	/**
	 * 学生获取某个课程的作业
	 */
	@Test
	@Rollback(value=false)
	@Transactional
	public void studentFindHomeworkList() {
		List<StudentHomework> studentHomeworks = shdao.findStudentHomeworksByClazzId(2l, 1l);
		System.out.println(studentHomeworks.size());
		StudentHomework studentHomework = studentHomeworks.get(0);
		Homework homework = studentHomework.getHomeworkissued_link_clazz().getHomeworkIssued().getHomework();
		List<Exercises> exercisesList = homework.getExercisesList();
//		Iterator<Exercises> iterator = exercisesList.iterator();
//		while(iterator.hasNext()) {
//			Exercises next = iterator.next();
//			next.setExercisesAnswer(null);
//		}
		List<Exercises> result = new ArrayList<Exercises>();
		for (Exercises exercises : exercisesList) {
			exercises.setExercisesAnswer(null);
			result.add(exercises);
		}
		for (Exercises exercises : result) {
			System.out.println(exercises);
		}
		
	}
	
	@Test
	@Rollback(value=false)
	public void studentFindHomeworkList2() {
		List<StudentHomework> studentHomeworks = shService.findStudentHomeworksByClazzId(2l, 1l);
		
		List<Exercises> exercisesList = shService.getStudentHomeworkExercisesList(studentHomeworks.get(0).getId());
		
		for (Exercises exercises : exercisesList) {
			exercises.setExercisesAnswer(null);
			System.out.println(exercises);
		}
		
	}
	
}
