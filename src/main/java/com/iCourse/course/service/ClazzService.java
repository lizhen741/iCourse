package com.iCourse.course.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.course.bean.Chapter;
import com.iCourse.course.bean.Clazz;
import com.iCourse.user.bean.Student;

public interface ClazzService {


//	Page<Clazz> findByCourseId(Clazz clazz, Pageable pageable);

	void update(Clazz clazz);

	int clazzAddStudents(Clazz clazz, List<Long> ids);

	int clazzDelStudents(Long clazz_id, Long[] ids);

	Clazz findBySerail(String clazz_serial_number);

	Page<Clazz> findByCourse(Specification<Clazz> where, Pageable pageable);

	void save(Clazz clazz, Long course_id);

	void joinClazz(Long clazz_id, Student student);

	List<Clazz> getClazzesForHomework(Long clazz_id);

	Page<Clazz> getClazzesByStudent(Long student_id,Pageable pageable);

	Page<Clazz> getClazzesByStudentSpec(Specification<Clazz> spec, Pageable pageable);

	List<Long> getClazzAllStudentId(Long clazz_id);

	

}
