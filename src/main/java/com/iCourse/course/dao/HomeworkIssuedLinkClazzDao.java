package com.iCourse.course.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.HomeworkIssuedLinkClazz;

public interface HomeworkIssuedLinkClazzDao extends PagingAndSortingRepository<HomeworkIssuedLinkClazz , Long>,JpaSpecificationExecutor<HomeworkIssuedLinkClazz>{

	@Query("select distinct hlc from "
			+ "HomeworkIssuedLinkClazz hlc inner join fetch Clazz c on c.id = ?1 "
			+ "inner join fetch HomeworkIssued h on h.id = ?2 where hlc.homeworkIssued.id = ?2 and hlc.clazz.id = ?1")
	public HomeworkIssuedLinkClazz findOneHomeworkIssuedLinkClazz(Long clazz_id,Long homeworkIssued_id);
}
