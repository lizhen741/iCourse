package com.iCourse.course.web.controller.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.AJAXresult;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Chapter;
import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.ChapterQueryDTO;
import com.iCourse.course.service.ChapterService;

@Controller
@RequestMapping("/student")
@CrossOrigin
public class ChapterStuController extends BaseController<Chapter>{
	@Autowired
	private ChapterService chapterService;
	
	
	@GetMapping("/chapter")
	@ResponseBody
	public Object getChapters(ChapterQueryDTO query) {
		start();
		
		try {
			List<Chapter> chapters = chapterService.findAll(query.getWhere(query));
			success(chapters);
		} catch (Exception e) {
			fail();
		}
		
		return end();
	}
	

	@GetMapping("/chapterbycourse")
	@ResponseBody
	public Object getChapterByCourse(ChapterQueryDTO query,PageInfo<Chapter> page) {
		start();
		
		page.initPage(1, 8);
		
		Pageable pageable = PageRequest.of(page.getPageno() - 1, page.getPagesize(), page.getSort());
		Page<Chapter> data = chapterService.findAll(query.getWhere(query),pageable);
		page.setReault(data);

		success(page);
		return end();
	}
	
	@GetMapping("/getOneChapter")
	@ResponseBody
	public Object getOneChapter(Long chapter_id) {
		start();
		
		Chapter entity = chapterService.getOneChapter(chapter_id);
		success(entity);
		return end();
	}
}
