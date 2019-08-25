package com.iCourse.homework;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.iCourse.BaseTest;
import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.bean.HomeworkIssued;
import com.iCourse.course.bean.HomeworkIssuedLinkClazz;
import com.iCourse.course.bean.StudentHomework;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.ExercisesDao;
import com.iCourse.course.dao.HomeworkDao;
import com.iCourse.course.dao.HomeworkIssuedDao;
import com.iCourse.course.dao.HomeworkIssuedLinkClazzDao;
import com.iCourse.course.dao.StudentHomeworkDao;
import com.iCourse.user.bean.Student;

public class AddData extends BaseTest{

	@Autowired
	private HomeworkDao hdao;
	@Autowired
	private ExercisesDao edao;
	@Autowired
	private HomeworkIssuedDao hidao;
	@Autowired
	private HomeworkIssuedLinkClazzDao hicdao;
	@Autowired
	private ClazzDao cdao;
	@Autowired
	private StudentHomeworkDao shdao;
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void addHomework() {
		List<Long> exercises_ids = new ArrayList<Long>();
		exercises_ids.add(1l);
		exercises_ids.add(2l);
		exercises_ids.add(3l);
		exercises_ids.add(6l);
//		exercises_ids.add(7l);
//		exercises_ids.add(8l);
//		exercises_ids.add(9l);
		
		Homework homework = new Homework();
		homework.setHomework_name("测试作业 java班");
		homework.setCourse_id(1l);
		
		List<Exercises> exercises = (List<Exercises>) edao.findAllById(exercises_ids);
		for (Exercises exercises2 : exercises) {
			homework.getExercisesList().add(exercises2);
		}
		hdao.save(homework);
	}
	
	/**
	 * 发布作业
	 */
	@Test
	@Rollback(value=false)
	@Transactional
	public void addHomeworkissued() {
		Homework homework = hdao.findById(3l).get();
		
		HomeworkIssued homeworkIssued = new HomeworkIssued();
		homeworkIssued.setBegin_time(new Date());
		homeworkIssued.setEnd_time(new Date());
		homeworkIssued.setHomework(homework);
		hidao.save(homeworkIssued);
		
		List<Long> clazz_ids = new ArrayList<Long>();
		clazz_ids.add(1l);
//		clazz_ids.add(3l);
		List<Clazz> clazzes = (List<Clazz>) cdao.findAllById(clazz_ids);
		
		for (Clazz clazz : clazzes) {
			HomeworkIssuedLinkClazz homeworkIssuedLinkClazz = new HomeworkIssuedLinkClazz();
			homeworkIssuedLinkClazz.setHomeworkIssued(homeworkIssued);
			homeworkIssuedLinkClazz.setClazz(clazz);
			hicdao.save(homeworkIssuedLinkClazz);
		}
		
	}
	/**
	 * 发布作业后 为作业相关的学生 创建studentHomework数据
	 */
	@Test
	@Rollback(value=false)
	@Transactional
	public void setStudentHomework() {
		HomeworkIssuedLinkClazz homeworkIssuedLinkClazz 
				= hicdao.findOneHomeworkIssuedLinkClazz(1l, 4l);
		Clazz clazz = homeworkIssuedLinkClazz.getClazz();
		List<Student> students = clazz.getStudents();
		
		for (Student student : students) {
			StudentHomework studentHomework = new StudentHomework();
			studentHomework.setHomeworkissued_link_clazz(homeworkIssuedLinkClazz);
			studentHomework.setStudent(student);
			shdao.save(studentHomework);
		}
	}
	
	
}
