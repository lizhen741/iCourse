package com.iCourse;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iCourse.course.dao.CategoryDao;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.CourseDao;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.dao.TeacherDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	locations={
		"classpath:spring/applicationContext-core.xml"
		,"classpath:spring/applicationContext-jpa.xml"
})   
public class BaseTest {
	

}
