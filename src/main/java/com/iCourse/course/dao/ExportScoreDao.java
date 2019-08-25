package com.iCourse.course.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.Clazz;

public interface ExportScoreDao extends PagingAndSortingRepository<Clazz , Long>,JpaSpecificationExecutor<Clazz>{

	@Query("select count(*) from HomeworkIssuedLinkClazz where clazz_id = ?1")
	Integer countHomeworkByCl_id(Long cl_id);
	
	@Query("select sum_grade from StudentHomework where student_id = ?1")
	List<Integer> getScoresByStuId(Long long1);
	
	@Modifying
	@Query("update StudentHomework h set h.sum_grade = 0 where h.sum_grade is null")
	List<Integer> updateGrade();
}
