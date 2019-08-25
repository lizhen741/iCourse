package com.iCourse.exercise;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.iCourse.BaseTest;
import com.iCourse.course.bean.Chapter;
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.ExercisesAnswer;
import com.iCourse.course.bean.ExercisesItem;
import com.iCourse.course.bean.DTO.ExercisesQueryDTO;
import com.iCourse.course.dao.ChapterDao;
import com.iCourse.course.dao.ExercisesDao;
import com.iCourse.course.dao.ExercisesItemDao;

public class AddData extends BaseTest{

	@Autowired
	private ExercisesDao exdao;
	@Autowired
	private ChapterDao chdao;
	@Autowired
	private ExercisesItemDao eidao;
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void findex() {
		ExercisesQueryDTO exercisesQueryDTO = new ExercisesQueryDTO();
		List<Exercises> findAll = (List<Exercises>) exdao.findAll(exercisesQueryDTO.getWhere(exercisesQueryDTO));
		for (Exercises exercises : findAll) {
			System.out.println(exercises.getExercises_type());
		}
		
		
	}
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void add1() {
		Exercises exercises = new Exercises();
		exercises.setExercises_content("7777选择题（）选出一个正确答案2222！");
		exercises.setExercises_type("选择题");
		exercises.setDifficulty(1);
		exercises.setCourse_id(2l);
		Chapter chapter = chdao.findById(3l).get();
		exercises.getChapters().add(chapter);
		
		ExercisesAnswer answer = new ExercisesAnswer();
		answer.setShort_answer("D");
		answer.setAnswer_explain("这不简简单单吗");
		exercises.setExercisesAnswer(answer);
		exdao.save(exercises);
		for (int i = 0; i < 4; i++) {
			ExercisesItem exercisesItem = new ExercisesItem();
			exercisesItem.setItem_content("xixi这题选D+"+i);
			exercisesItem.setExercises(exercises);
			eidao.save(exercisesItem);
		}
	}
	@Test
	@Rollback(value=false)
	@Transactional
	public void add2() {
		Exercises exercises = new Exercises();
		exercises.setExercises_content("关于这个问题填空【      】");
		exercises.setExercises_type("填空题");
		exercises.setDifficulty(1);
		exercises.setCourse_id(2l);
		Chapter chapter = chdao.findById(2l).get();
		exercises.getChapters().add(chapter);
		
		ExercisesAnswer answer = new ExercisesAnswer();
		answer.setShort_answer("我也不知道");
		answer.setAnswer_explain("这不简简单单吗");
		exercises.setExercisesAnswer(answer);
		exdao.save(exercises);
	}
	@Test
	@Rollback(value=false)
	@Transactional
	public void add3() {
		Exercises exercises = new Exercises();
		exercises.setExercises_content("关于这个问题填空说说你的看法？");
		exercises.setExercises_type("简答题");
		exercises.setDifficulty(2);
		exercises.setCourse_id(2l);
		Chapter chapter = chdao.findById(2l).get();
		exercises.getChapters().add(chapter);
		
		ExercisesAnswer answer = new ExercisesAnswer();
		answer.setShort_answer("我看尼玛");
		answer.setAnswer_explain("这不简简单单吗");
		exercises.setExercisesAnswer(answer);
		exdao.save(exercises);
	}
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void find() {
		ExercisesQueryDTO q = new ExercisesQueryDTO();
		List<Long> ids = new ArrayList<Long>();
//		ids.add(2l);
		ids.add(3l);
		q.setChapter_ids(ids);
		List<Exercises> findAll = exdao.findAll(q.getWhere(q));
		for (Exercises exercises : findAll) {
			
		}
	}
	
}
