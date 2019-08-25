package com.iCourse.course;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.iCourse.BaseTest;
import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.ClazzQueryDTO;
import com.iCourse.course.bean.DTO.CourseQueryDTO;
import com.iCourse.course.dao.CategoryDao;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.CourseDao;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.dao.TeacherDao;

public class findTest extends BaseTest{
	@Autowired
	private CourseDao cdao;
	@Autowired
	private TeacherDao tdao;
	@Autowired
	private CategoryDao cadao;
	@Autowired
	private StudentDao stdao;
	@Autowired
	private ClazzDao cldao;
	
	@Test
	@Rollback
	@Transactional
	public void findCourseByTeacherId() {
//		List<Course> courses = cdao.findCourseByTeacherId(1l);
//		for (Course course : courses) {
//			System.out.println(course);
//		}
		CourseQueryDTO query = new CourseQueryDTO();
//		query.setTeacher_name("李");
		query.setTeacher_id(1l);
		List<Course> findAll = cdao.findAll(query.getWhere(query));
		for (Course course : findAll) {
			System.out.println(course.getCourse_name());
		}
//		List<Long> ids = cdao.getOpenCourseIds(1l);
//		for (Long id : ids) {
//			System.out.println(id);
//		}
	}
	
	@Test
	@Rollback
	@Transactional
	public void findClazz() {
		ClazzQueryDTO query = new ClazzQueryDTO();
		query.setClazz_status(true);
		query.setCourse_id(1l);
		List<Clazz> clazzes = cldao.findAll(query.getWhere(query));
		for (Clazz clazz : clazzes) {
			System.out.println(clazz.getClazz_name());
		}
	}
	
	@Test
	@Rollback
	@Transactional
	public void findClazzByStudent() {
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(1 - 1, 3,sort);
		Page<Clazz> clazzesByStudent = cldao.getClazzesByStudent(2l, pageable);
		List<Clazz> content = clazzesByStudent.getContent();
		for (Clazz clazz : content) {
			System.out.println(clazz.getClazz_name());
		}
	}
	@Test
	@Rollback
	@Transactional
	public void findClazzByStudent2() {
		ClazzQueryDTO query = new ClazzQueryDTO();
		
		query.setStudent_id(2l);
		List<Clazz> findAll = cldao.findAll(query.getWhere(query));
		for (Clazz clazz : findAll) {
			System.out.println(clazz.getClazz_name());
		}
	}
}
