package com.iCourse.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.user.bean.Teacher;

public interface TeacherDao extends PagingAndSortingRepository<Teacher , Long> ,JpaSpecificationExecutor<Teacher>{

	@Query("select distinct t from Teacher t inner join fetch t.courses")
	List<Teacher> findTeacherAndCourse();

	@Query("from Teacher t where t.teacher_num = ?1")
	Teacher findByTeacherNum(String teacher_num);



}
