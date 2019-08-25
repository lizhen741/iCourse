package com.iCourse.course.web.controller.teacher;

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
@RequestMapping("/teacher")
@CrossOrigin
public class ChapterTeaController extends BaseController<Chapter>{
	@Autowired
	private ChapterService chapterService;
	
	
	/**
	 * 教师为课堂添加章节
	 * @param chapter
	 * @return
	 */
	@PostMapping("/chapter")
	@ResponseBody
	public Object addChapter(Chapter chapter) {
		start();
		Chapter chapter2 = new Chapter();

		chapter.setDisable(!chapter.getLeaf());
		try {
			
			Long id = chapterService.save(chapter);
			
			chapter2.setId(id);
			success(chapter2);
		} catch (Exception e) {
			fail();
		}
		
		return end();
	}
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
	@PutMapping("/chapter")
	@ResponseBody
	public String updateChapter( Chapter chapter) {
//		Chapter chapter = new Chapter();
//		chapter.setChapter_name(chapter_name);
//		chapter.setId(id);
		chapterService.updateName(chapter);
		return "ojbk";
	}
	@DeleteMapping("/chapter")
	@ResponseBody
	public String deleteChapter(Long id) {
		chapterService.deleteChapter(id);
		return "ojbk";
	}

	@GetMapping("/chapterbycourse")
	@ResponseBody
	public Object getChapterByCourse(ChapterQueryDTO query,PageInfo<Chapter> pageInfo) {
		start();
		
		pageInfo.initPage(1, 8);
		
		Pageable pageable = PageRequest.of(pageInfo.getPageno() - 1, pageInfo.getPagesize(), pageInfo.getSort());
		Page<Chapter> data = chapterService.findAll(query.getWhere(query),pageable);
		
		pageInfo.setReault(data);;

		success(pageInfo);
		return end();
	}
}
