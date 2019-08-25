package com.iCourse.clazz;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.iCourse.BaseTest;
import com.iCourse.course.dao.ClazzDao;

public class FindTest extends BaseTest{
	@Autowired
	private ClazzDao clazzDao;
	
	
	@Test
	public void demo1(){
		List<Long> ids = clazzDao.getClazzAllStudentId(4l);
		for (Long id : ids) {
			System.out.println(id);
		}
	}
	
}
