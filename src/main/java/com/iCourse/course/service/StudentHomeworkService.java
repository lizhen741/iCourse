package com.iCourse.course.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.StudentHomework;

public interface StudentHomeworkService {

	List<StudentHomework> findStudentHomeworksByClazzId(Long student_id, Long clazz_id);

	List<Exercises> getStudentHomeworkExercisesList(Long studentHomework_id);

	Map<String, Object> findStudentHomeworksByClazzId(Long id, Long clazz_id, Pageable pageable);

	StudentHomework findById(Long stuHomework_id);

	void updateAnswer(Long stuHwRecord_id, String newAnswer);

	Page<StudentHomework> getStudentHomeworks(Specification<StudentHomework> spec, Pageable pageable);

	void setStudentAnswerRecordGrade(Long id, Integer grade);

	Integer studentFinishHomework(Long stuHomework_id, Long student_id);

	void setStuHomeworkTotalGrade(Long stuHomeword_id, Integer totalGrade);

	Date getHomeworkEndTime(Long stuHomework_id);

}
