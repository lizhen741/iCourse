package com.iCourse.course.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Category;
import com.iCourse.course.dao.CategoryDao;
import com.iCourse.course.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryDao categoryDao;
	@Override
	public void save(Category category) {
		categoryDao.save(category);
		
	}

	@Override
	public List<Category> findAll() {	
		return (List<Category>) categoryDao.findAll();
	}

}
