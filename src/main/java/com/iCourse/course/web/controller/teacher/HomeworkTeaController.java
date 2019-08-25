package com.iCourse.course.web.controller.teacher;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.bean.DTO.HomeworkQueryDTO;
import com.iCourse.course.service.HomeworkService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class HomeworkTeaController extends BaseController<Homework>{
	@Autowired
	private HomeworkService homeworkService;
	
	
	@PostMapping("/homework")
	@ResponseBody
	public Object createHomework(@RequestBody Homework homework) {
		start();
		
		homework.setCreate_time(new Date());
		homeworkService.save(homework);
		success();
		
		return end();
	}
	
	@PostMapping("/getHomework")
	@ResponseBody
	public Object getHomework(HomeworkQueryDTO query,PageInfo<Homework> pageInfo) {
		start();
		
		pageInfo.initPage(1, 8);
		Pageable pageable = PageRequest.of(pageInfo.getPageno() - 1, pageInfo.getPagesize(), pageInfo.getSort());
		Page<Homework> data = homeworkService.getHomework(query.getWhere(query),pageable);
		pageInfo.setReault(data);
		
		success(pageInfo);
		return end();
	}
	
	@GetMapping("/getOneHomework")
	@ResponseBody
	public Object getHomework(Long homework_id) {
		start();
		Homework homework = homeworkService.getHomework(homework_id);
		for (Exercises exercises : homework.getExercisesList()) {
			exercises.setChapters(null);
		}
		success();
		
		return homework;
	}

	
}
