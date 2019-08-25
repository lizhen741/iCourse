package com.iCourse.course.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.Video;

public interface VideoDao extends PagingAndSortingRepository<Video, Long> ,JpaSpecificationExecutor<Course>{

}
