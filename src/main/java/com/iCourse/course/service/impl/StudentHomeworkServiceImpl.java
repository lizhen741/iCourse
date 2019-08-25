package com.iCourse.course.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.bean.HomeworkIssued;
import com.iCourse.course.bean.StudentHomework;
import com.iCourse.course.bean.StudentHomeworkPack;
import com.iCourse.course.dao.StudentExercisesAnswerRecordDao;
import com.iCourse.course.dao.StudentHomeworkDao;
import com.iCourse.course.service.StudentHomeworkService;

@Service
@Transactional
public class StudentHomeworkServiceImpl implements StudentHomeworkService {

	@Autowired
	private StudentHomeworkDao studentHomeworkDao;
	@Autowired
	private StudentExercisesAnswerRecordDao studentExercisesAnswerRecordDao;

	@Override
	public List<StudentHomework> findStudentHomeworksByClazzId(Long student_id, Long clazz_id) {
		return studentHomeworkDao.findStudentHomeworksByClazzId(student_id, clazz_id);

	}

	@Override
	public List<Exercises> getStudentHomeworkExercisesList(Long studentHomework_id) {

		StudentHomework studentHomework = studentHomeworkDao.findById(studentHomework_id).get();
		Homework homework = studentHomework.getHomeworkissued_link_clazz().getHomeworkIssued().getHomework();
		List<Exercises> exercisesList = homework.getExercisesList();
		for (Exercises exercises : exercisesList) {
			break;
		}
		return exercisesList;
	}

	@Override
	public Map<String, Object> findStudentHomeworksByClazzId(Long student_id, Long clazz_id, Pageable pageable) {
		Page<StudentHomework> page = studentHomeworkDao.findStudentHomeworksByClazzId(student_id, clazz_id, pageable);
		Map<String, Object> map = new HashMap<String, Object>();

		List<StudentHomework> content = page.getContent();
		List<StudentHomeworkPack> data = new ArrayList<StudentHomeworkPack>();
		for (StudentHomework studentHomework : content) {
			StudentHomeworkPack studentHomeworkPack = new StudentHomeworkPack();
			studentHomeworkPack.setGrade(studentHomework.getSum_grade());
			
			HomeworkIssued homeworkIssued = studentHomework.getHomeworkissued_link_clazz().getHomeworkIssued();
			studentHomeworkPack.setStatus(studentHomework.getStatus());
			studentHomeworkPack.setStuHomework_id(studentHomework.getId());
			studentHomeworkPack.setHomework_id(homeworkIssued.getHomework().getId());
			studentHomeworkPack.setEnd_time(homeworkIssued.getEnd_time());
			studentHomeworkPack.setBegin_time(homeworkIssued.getBegin_time());
			
			studentHomeworkPack.setHomework_name(homeworkIssued.getHomework().getHomework_name());
			data.add(studentHomeworkPack);
		}

		map.put("pageable", page);
		map.put("data", data);
		return map;
	}

	@Override
	public StudentHomework findById(Long stuHomework_id) {
		return studentHomeworkDao.findById(stuHomework_id).get();

	}

	@Override
	public void updateAnswer(Long stuHwRecord_id, String newAnswer) {
		studentExercisesAnswerRecordDao.updateAnswer(stuHwRecord_id,newAnswer);
	}

	@Override
	public Page<StudentHomework> getStudentHomeworks(Specification<StudentHomework> spec, Pageable pageable) {
		return studentHomeworkDao.findAll(spec, pageable);
	}

	@Override
	public void setStudentAnswerRecordGrade(Long id, Integer grade) {
		studentExercisesAnswerRecordDao.setStudentAnswerRecordGrade(id,grade);
	}

	@Override
	public Integer studentFinishHomework(Long stuHomework_id, Long student_id) {
		StudentHomework studentHomework = studentHomeworkDao.findById(stuHomework_id).get();
		if(studentHomework.getStudent().getId()!=student_id) {
			return -2;
		}
		else {
			studentHomework.setFinish_time(new Date());
			studentHomework.setStatus("待批阅");
			return 1;
		}
	}

	@Override
	public void setStuHomeworkTotalGrade(Long stuHomeword_id, Integer totalGrade) {
		studentHomeworkDao.setStuHomeworkTotalGrade(stuHomeword_id, totalGrade);
	}

	@Override
	public Date getHomeworkEndTime(Long stuHomework_id) {
		StudentHomework studentHomework = studentHomeworkDao.findById(stuHomework_id).get();
		HomeworkIssued homeworkIssued = studentHomework.getHomeworkissued_link_clazz().getHomeworkIssued();
		return homeworkIssued.getEnd_time();
	}

}
