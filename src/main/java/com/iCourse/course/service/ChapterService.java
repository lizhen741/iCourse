package com.iCourse.course.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.course.bean.Chapter;

public interface ChapterService {

	Long save(Chapter chapter);
	
	List<Chapter> findAll(Specification<Chapter> spec);

	void deleteChapter(Long id);

	void updateName(Chapter chapter);

	Page<Chapter> findAll(Specification<Chapter> spec, Pageable pageable);

	Chapter getOneChapter(Long chapter_id);

	


}
