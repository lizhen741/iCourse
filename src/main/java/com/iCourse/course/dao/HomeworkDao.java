package com.iCourse.course.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.Homework;

public interface HomeworkDao extends PagingAndSortingRepository<Homework , Long>,JpaSpecificationExecutor<Homework>{

}
