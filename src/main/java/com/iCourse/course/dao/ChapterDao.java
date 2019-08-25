package com.iCourse.course.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.Chapter;

public interface ChapterDao extends PagingAndSortingRepository<Chapter , Long>,JpaSpecificationExecutor<Chapter>{

	@Modifying
	@Query("delete Chapter c where c.chapter_parent = ?1 ")
	void deleteByParent(Long id);

	@Modifying
	@Query("update Chapter c set c.chapter_name = ?1 where c.id = ?2 ")
	void updateName(String chapter_name,Long id);


}
