package com.iCourse.course.web.controller.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.bean.HomeworkIssued;
import com.iCourse.course.bean.DTO.HomeworkIssuedQueryDTO;
import com.iCourse.course.service.HomeworkIssuedService;

@Controller
@RequestMapping("teacher")
@CrossOrigin
public class HomeworkIssuedTeaController extends BaseController<HomeworkIssued>{

	@Autowired
	private HomeworkIssuedService homeworkIssuedService;
	
	
	/**
	 * 查看已发布的作业列表
	 * @param query
	 * @param pageInfo
	 * @param req
	 * @return
	 */
	@PostMapping("/getHomeworkIssued")
	@ResponseBody
	public Object getHomework(HomeworkIssuedQueryDTO query, PageInfo<HomeworkIssued> pageInfo,
			HttpServletRequest req) {
		start();
	
		pageInfo.initPage(1, 8);
		Pageable pageable = PageRequest.of(pageInfo.getPageno() - 1, pageInfo.getPagesize(), pageInfo.getSort());
		Page<HomeworkIssued> data = homeworkIssuedService.teacherGetHomework(query.getWhere(query), pageable);
		
		pageInfo.setReault(data);

		success(pageInfo);

		return end();
	}
	
	/**
	 * 发布新作业
	 * @param homeworkIssued
	 * @param clazz_ids
	 * @param homework_id
	 * @return
	 */
	@PostMapping("/saveHomeworkIssued")
	@ResponseBody
	public Object saveHomeworkIssued(HomeworkIssued homeworkIssued, String clazz_ids,Long homework_id) {
		start();
		
		
		List<Clazz> clazzes = new ArrayList<Clazz>();
		if(!StringUtils.isEmpty(clazz_ids)) {
			String[] id = clazz_ids.split(",");
			for (String i : id) {
				Clazz clazz = new Clazz();
				clazz.setId(Long.parseLong(i));
				clazzes.add(clazz);
			}
		}

		Homework homework = new Homework();
		homework.setId(homework_id);
		homeworkIssued.setHomework(homework);
		homeworkIssuedService.save(homeworkIssued,clazzes);
		success();
		
		return end();
	}
	
	/**
	 * 获取一个发布的作业（查看作业时调用）
	 * @param homeworkIssued_id
	 * @return
	 */
	@GetMapping("/getHomeworkIssuedById")
	@ResponseBody
	public Object getHomeworkIssuedById(Long homeworkIssued_id) {
		start();
		
		HomeworkIssued entity = homeworkIssuedService.findById(homeworkIssued_id);
		success(entity);
				
		return end();
		
	}
	
	
}
