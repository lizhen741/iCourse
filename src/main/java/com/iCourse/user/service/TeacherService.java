package com.iCourse.user.service;

import java.io.InputStream;

import com.iCourse.user.bean.Teacher;

public interface TeacherService {
	public void save(Teacher teacher);

	public Teacher findById(String teacher_num);
}
