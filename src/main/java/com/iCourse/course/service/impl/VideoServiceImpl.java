package com.iCourse.course.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Chapter;
import com.iCourse.course.bean.Video;
import com.iCourse.course.dao.ChapterDao;
import com.iCourse.course.dao.VideoDao;
import com.iCourse.course.service.VideoService;
@Service
@Transactional
public class VideoServiceImpl implements VideoService{

	@Autowired
	private VideoDao videoDao;
	@Autowired
	private ChapterDao chapterDao;
	@Override
	public void save(Video video) {
//		videoDao.save(video);
		Chapter chapter = chapterDao.findById(video.getChapter_id()).get();
		chapter.setVideo(video);
	}

}
