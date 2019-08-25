package com.iCourse.course.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.ExercisesItem;

public interface ExercisesItemDao extends CrudRepository<ExercisesItem, Long> {




}
