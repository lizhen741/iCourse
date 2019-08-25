package com.iCourse.user.service.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iCourse.user.bean.Teacher;
import com.iCourse.user.dao.TeacherDao;
import com.iCourse.user.service.TeacherService;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	private TeacherDao teacherDao;
	
	public void save(Teacher teacher) {
		teacherDao.save(teacher);
	}

	@Override
	public Teacher findById(String teacher_num) {
		return teacherDao.findByTeacherNum(teacher_num);
	}

}
