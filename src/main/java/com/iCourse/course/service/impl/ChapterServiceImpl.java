package com.iCourse.course.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Chapter;
import com.iCourse.course.dao.ChapterDao;
import com.iCourse.course.service.ChapterService;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService{

	@Autowired
	private ChapterDao chapterDao;
	@Override
	public Long save(Chapter chapter) {
		chapterDao.save(chapter);
		System.out.println(chapter.getId());
		return chapter.getId();
	}
	@Override
	public List<Chapter> findAll(Specification<Chapter> spec) {	
		return chapterDao.findAll(spec);
	}
	@Override
	public void deleteChapter(Long id) {
		chapterDao.deleteById(id);
		chapterDao.deleteByParent(id);
		
	}
	@Override
	public void updateName(Chapter chapter) {
		chapterDao.updateName(chapter.getChapter_name(),chapter.getId());
		
	}
	@Override
	public Page<Chapter> findAll(Specification<Chapter> spec, Pageable pageable) {
		return chapterDao.findAll(spec, pageable);
	}
	@Override
	public Chapter getOneChapter(Long chapter_id) {
		return chapterDao.findById(chapter_id).get();
	}
	
	

}
