package com.iCourse.course.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.Exercises;

public interface ExercisesDao extends PagingAndSortingRepository<Exercises, Long>,JpaSpecificationExecutor<Exercises> {


}
