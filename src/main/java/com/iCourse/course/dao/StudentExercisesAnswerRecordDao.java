package com.iCourse.course.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iCourse.course.bean.StudentExercisesAnswerRecord;

public interface StudentExercisesAnswerRecordDao extends CrudRepository<StudentExercisesAnswerRecord, Long>{

	@Modifying
	@Query("update StudentExercisesAnswerRecord s set s.answer = ?2 where s.id = ?1")
	void updateAnswer(Long stuHwRecord_id, String newAnswer);

	@Modifying
	@Query("update StudentExercisesAnswerRecord s set s.grade = ?2 where s.id = ?1")
	void setStudentAnswerRecordGrade(Long id, Integer grade);

}
