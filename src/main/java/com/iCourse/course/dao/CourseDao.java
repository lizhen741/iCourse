package com.iCourse.course.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.Course;

public interface CourseDao extends PagingAndSortingRepository<Course, Long>, JpaSpecificationExecutor<Course> {

	@Query(value = "SELECT * from t_course "
			+ "where id in (SELECT course_id from t_clazz "
			+ "where id in (SELECT clazz_id from t_clazz_student "
			+ "where student_id = ?1))", nativeQuery = true)
	Page<Course> findStudentCourseList(Long student_id,Pageable pageable);

	@Query(value = "SELECT * from t_course "
			+ "where teacher = ?1", nativeQuery = true)
	List<Course> findCourseByTeacherId(Long teacher_id, Pageable pageable);

	@Query("select distinct new Course(c.id,c.course_name) from Course c where c.teacher.id = ?1")
	List<Course> findCourseByTeacherId(Long teacher_id);

	@Query("select c.id from Course c where c.course_open = true and c.teacher.id = ?1")
	List<Long> getOpenCourseIds(Long teacher_id);
}
