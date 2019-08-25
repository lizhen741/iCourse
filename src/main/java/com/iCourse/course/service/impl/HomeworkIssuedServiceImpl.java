package com.iCourse.course.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.bean.HomeworkIssued;
import com.iCourse.course.bean.HomeworkIssuedLinkClazz;
import com.iCourse.course.bean.StudentExercisesAnswerRecord;
import com.iCourse.course.bean.StudentHomework;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.HomeworkDao;
import com.iCourse.course.dao.HomeworkIssuedDao;
import com.iCourse.course.dao.HomeworkIssuedLinkClazzDao;
import com.iCourse.course.dao.StudentExercisesAnswerRecordDao;
import com.iCourse.course.dao.StudentHomeworkDao;
import com.iCourse.course.service.HomeworkIssuedService;
import com.iCourse.user.bean.Student;

@Service
@Transactional
public class HomeworkIssuedServiceImpl implements HomeworkIssuedService{
	@Autowired
	private HomeworkIssuedDao homeworkIssuedDao;
	@Autowired
	private HomeworkIssuedLinkClazzDao homeworkIssuedLinkClazzDao;
	@Autowired
	private StudentHomeworkDao studentHomeworkDao;
	@Autowired
	private ClazzDao clazzDao;
	@Autowired
	private HomeworkDao homeworkDao;
	@Autowired
	private StudentExercisesAnswerRecordDao studentExercisesAnswerRecordDao;

	@Override
	public Page<HomeworkIssued> teacherGetHomework(Specification<HomeworkIssued> spec, Pageable pageable) {
		return homeworkIssuedDao.findAll(spec, pageable);
	}

	@Override
	public void save(HomeworkIssued homeworkIssued,List<Clazz> clazzes) {
		homeworkIssuedDao.save(homeworkIssued);
		Homework homework = homeworkDao.findById(homeworkIssued.getHomework().getId()).get();
		
		for (Clazz clazz : clazzes) {
			HomeworkIssuedLinkClazz homeworkIssuedLinkClazz = new HomeworkIssuedLinkClazz();
			homeworkIssuedLinkClazz.setHomeworkIssued(homeworkIssued);
			homeworkIssuedLinkClazz.setClazz(clazz);
			homeworkIssuedLinkClazz.setClazzId(clazz.getId());
			homeworkIssuedLinkClazz.setHomeworkIssuedId(homeworkIssued.getId());
			homeworkIssuedLinkClazzDao.save(homeworkIssuedLinkClazz);
			List<Student> students = clazzDao.findById(clazz.getId()).get().getStudents();
			for (Student student : students) {
				StudentHomework studentHomework = new StudentHomework();
				studentHomework.setHomeworkissued_link_clazz(homeworkIssuedLinkClazz);
				studentHomework.setStudent(student);
				studentHomework.setHLC_id(homeworkIssuedLinkClazz.getId());
				studentHomework.setStatus("进行中");
				studentHomeworkDao.save(studentHomework);
				List<Exercises> exercisesList = homework.getExercisesList();
				for (Exercises exercises : exercisesList) {
					StudentExercisesAnswerRecord stuAns = new StudentExercisesAnswerRecord();
					stuAns.setStudentHomework(studentHomework);
					stuAns.setExercises_id(exercises.getId());
					if("单选题".equals(exercises.getExercises_type())) {
						stuAns.setMax_grade(homework.getSinglechoice_grade());
					}
					else if("多选题".equals(exercises.getExercises_type())) {
						stuAns.setMax_grade(homework.getMultiplechoice_grade());
					}
					else if("判断题".equals(exercises.getExercises_type())) {
						stuAns.setMax_grade(homework.getJudgment_grade());
					}
					else if("填空题".equals(exercises.getExercises_type())) {
						stuAns.setMax_grade(homework.getCompletion_grade());
					}
					else if("简答题".equals(exercises.getExercises_type())) {
						stuAns.setMax_grade(homework.getShortanswer_grade());
					}
					studentExercisesAnswerRecordDao.save(stuAns);
					
				}
				
			}
		}
	}

	@Override
	public HomeworkIssued findById(Long homeworkIssued_id) {
		return homeworkIssuedDao.findById(homeworkIssued_id).get();
	}
	
	

}
