package com.iCourse.course.web.controller.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.util.AJAXresult;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Category;
import com.iCourse.course.service.CategoryService;

@Controller
@RequestMapping("/student")
@CrossOrigin
public class CategoryStuController extends BaseController<Category>{
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("category")
	@ResponseBody
	public Object addCategory(Category category) {
		start();
		
		try {
			categoryService.save(category);
			success();
		} catch (Exception e) {
			fail();
		}
		
		return end();
	}
	
	@GetMapping("category")
	@ResponseBody
	public Object getCategory(Category c) {
		start();
		
		List<Category> list = categoryService.findAll();
		success(list);
		
		return end();
	}
	
}
