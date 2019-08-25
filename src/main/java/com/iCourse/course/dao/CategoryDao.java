package com.iCourse.course.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.Category;
import com.iCourse.course.bean.Course;

public interface CategoryDao extends CrudRepository<Category , Long>  {

}
