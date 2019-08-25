package com.iCourse.course.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.CourseQueryDTO;
import com.iCourse.user.bean.Teacher;

public interface CourseService {
	public void save(Teacher teacher,Course course);
//	public void update(Course course);

	public Course findById(long id);
	
	public void delete(Long id);

	public List<Course> findAll(CourseQueryDTO query);

	public Page<Course> findAll(CourseQueryDTO query, Pageable pageable);

	public void deleteById(long id);

	public Page<Course> findStudentCourseList(Long student_id,Pageable pageable);


	public List<Course> getCoursesForSettingPower(Long teacher_id);

	public List<Long> getOpenCourseIds(Long teacher_id);

	public void setCourseOpen(List<Long> course_ids);

	public void closeCourseOpen(List<Long> course_ids);

	public Page<Course> findAll(Specification<Course> spec, Pageable pageable);

	public List<Course> findAllById(List<Long> course_ids);


}
