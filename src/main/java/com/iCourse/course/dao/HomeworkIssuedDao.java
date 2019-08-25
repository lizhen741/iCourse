package com.iCourse.course.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.course.bean.HomeworkIssued;

public interface HomeworkIssuedDao extends PagingAndSortingRepository<HomeworkIssued , Long>,JpaSpecificationExecutor<HomeworkIssued>{

}
