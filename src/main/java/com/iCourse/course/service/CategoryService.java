package com.iCourse.course.service;

import java.util.List;

import com.iCourse.course.bean.Category;

public interface CategoryService {
	void save(Category category);
	
	List<Category> findAll();
}
