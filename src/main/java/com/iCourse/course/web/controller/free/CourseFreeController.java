package com.iCourse.course.web.controller.free;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.CourseQueryDTO;
import com.iCourse.course.service.CourseService;

@Controller
@RequestMapping("/free")
@CrossOrigin
public class CourseFreeController extends BaseController<Course>{
	@Autowired
	private CourseService courseService;
	
	
	@PostMapping("/getCourses")
	@ResponseBody
	public Object getCourses(CourseQueryDTO query,PageInfo<Course> pageInfo) {
		start();
		
		pageInfo.initPage(1, 8);
		query.setCourse_open(true);
		Pageable pageable = PageRequest.of(pageInfo.getPageno() - 1, pageInfo.getPagesize(), pageInfo.getSort());
		Page<Course> data = courseService.findAll(query.getWhere(query),pageable);
		pageInfo.setReault(data);
		success(pageInfo);
		
		return end();
	}
	
	@GetMapping("/course/{id}")
	@ResponseBody
	public Object findOneCourse(@PathVariable long id) {
		start();

		Course course = courseService.findById(id);
		if(course.getCourse_open())
			success(course);
		else
			fail();
		return end();
	}
}
