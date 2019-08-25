package com.iCourse.course.web.controller.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.Const;
import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Category;
import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.CourseQueryDTO;
import com.iCourse.course.service.CourseService;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.dao.TeacherDao;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class CourseTeaController extends BaseController<Course> {
	@Autowired
	private CourseService courseService;
	@Autowired
	private TeacherDao ts;

	/**
	 * 添加课程
	 * @param course
	 * @param req
	 * @return
	 */
	@PostMapping("/course")
	@ResponseBody
	public Object createCourse(Course course,Long category_id,HttpServletRequest req) {
		start();

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);

		
		Teacher teacher = ts.findById(account.getTeacher().getId()).get();

		try {
			Category category = new Category();
			category.setId(category_id);
			course.setCategory(category);
			courseService.save(teacher, course);
			success();
		} catch (Exception e) {
			fail();
		}

		return end();
	}

	/**
	 * 获取某个课程
	 * @param id
	 * @return
	 */
	@GetMapping("/course/{id}")
	@ResponseBody
	public Object findOneCourse(@PathVariable long id) {
		start();
		Course course = courseService.findById(id);
		course.setChapters(null);
		success(course);
		return end();
	}

	/**
	 * 获取教师课程列表
	 * @param query
	 * @param pageInfo
	 * @param req
	 * @return
	 */
	@GetMapping("/courses")
	@ResponseBody
	public Object findAll(CourseQueryDTO query, PageInfo<Course> pageInfo, HttpServletRequest req) {
		start();

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);

		/*
		 * Teacher teacher = ts.findById(account.getTeacher().getId()).get();
		 */
		Teacher teacher = ts.findById(account.getTeacher().getId()).get();
		query.setTeacher_id(teacher.getId());

		pageInfo.initPage(1, 8);

		Pageable pageable = PageRequest.of(pageInfo.getPageno() - 1, pageInfo.getPagesize(), pageInfo.getSort());
		Page<Course> data = courseService.findAll(query, pageable);
		pageInfo.setReault(data);

		success(pageInfo);

		return end();
	}

	/**
	 * 获取课程数据，用于设置课程是否公开
	 * 
	 * @param query
	 * @param req
	 * @return
	 */
	@GetMapping("/getCoursesForSettingPower")
	@ResponseBody
	public Object getCoursesForSettingPower(HttpServletRequest req) {
		start();

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);

		Teacher teacher = ts.findById(account.getTeacher().getId()).get();

		List<Course> courses = courseService.getCoursesForSettingPower(teacher.getId());
		success(courses);

		return end();
	}

	/**
	 * 获取已公开的课程的id
	 * 
	 * @param req
	 * @return
	 */
	@GetMapping("/getOpenCourseIds")
	@ResponseBody
	public Object getOpenCourseIds(HttpServletRequest req) {

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);

		List<Long> courseIds = courseService.getOpenCourseIds(account.getTeacher().getId());

		return courseIds;
	}

	/**
	 * 公开课程
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("/setCourseOpen")
	@ResponseBody
	public Object setCourseOpen(String ids, HttpServletRequest req) {
		start();
		Boolean flag = true;
		List<Long> course_ids = new ArrayList<Long>();
		String[] id = ids.split(",");
		for (String i : id) {
			course_ids.add(Long.parseLong(i));
		}

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		List<Course> courses = courseService.findAllById(course_ids);

		for (Course course2 : courses) {
			if (account.getTeacher().getId() != course2.getTeacher().getId()) {
				fail("你与这些课程无关");
				flag = false;
				break;
			}
		}

		if (flag) {
			courseService.setCourseOpen(course_ids);
			success();
		}

		return end();
	}

	/**
	 * 取消课程公开
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("/closeCourseOpen")
	@ResponseBody
	public Object closeCourseOpen(String ids,HttpServletRequest req) {
		start();
		
		Boolean flag = true;
		List<Long> course_ids = new ArrayList<Long>();
		String[] id = ids.split(",");
		for (String i : id) {
			course_ids.add(Long.parseLong(i));
		}

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		List<Course> courses = courseService.findAllById(course_ids);

		for (Course course2 : courses) {
			if (account.getTeacher().getId() != course2.getTeacher().getId()) {
				fail("你与这些课程无关");
				flag = false;
				break;
			}
		}
		if(flag) {
			courseService.closeCourseOpen(course_ids);

			success();
		}
		
		return end();
	}
}
