package com.iCourse.course.web.controller.free;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Chapter;
import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.ChapterQueryDTO;
import com.iCourse.course.service.ChapterService;
import com.iCourse.course.service.CourseService;

@Controller
@RequestMapping("/free")
@CrossOrigin
public class ChapterFreeController extends BaseController<Chapter>{

	@Autowired
	private ChapterService chapterService;
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/chapterbycourse")
	@ResponseBody
	public Object getChapterByCourse(ChapterQueryDTO query,PageInfo<Chapter> page) {
		start();
		
		
		Course course = courseService.findById(query.getCourse_id());
		if(!course.getCourse_open())
			fail();
		else {
			page.initPage(1, 8);
			
			Pageable pageable = PageRequest.of(page.getPageno() - 1, page.getPagesize(), page.getSort());
			Page<Chapter> data = chapterService.findAll(query.getWhere(query),pageable);
			page.setReault(data);

			success(page);
		}
		return end();
	}
}
