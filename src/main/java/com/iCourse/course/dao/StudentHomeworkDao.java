package com.iCourse.course.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.StudentHomework;

public interface StudentHomeworkDao extends PagingAndSortingRepository<StudentHomework , Long>,JpaSpecificationExecutor<StudentHomework>{

	@Query("select distinct sh from StudentHomework sh where sh.student.id = ?1 and sh.homeworkissued_link_clazz.clazz.id = ?2")
			//+ "inner join fetch HomeworkIssuedLinkClazz hlc on hlc.clazz.id = ?2")
	public List<StudentHomework> findStudentHomeworksByClazzId(Long student_id,Long clazz_id);

	@Query("select distinct sh from StudentHomework sh where sh.student.id = ?1 and sh.homeworkissued_link_clazz.clazz.id = ?2")
	public Page<StudentHomework> findStudentHomeworksByClazzId(Long student_id, Long clazz_id, Pageable pageable);

	@Modifying
	@Query("update StudentHomework s set s.sum_grade = ?2,s.status = '已批阅' where s.id =?1")
	public void setStuHomeworkTotalGrade(Long stuHomeword_id, Integer totalGrade);

}
