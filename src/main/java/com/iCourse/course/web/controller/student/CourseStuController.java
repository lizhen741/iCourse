package com.iCourse.course.web.controller.student;

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
import com.iCourse.common.util.AJAXresult;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.CourseQueryDTO;
import com.iCourse.course.service.CourseService;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.dao.TeacherDao;

@Controller
@RequestMapping("/student")
@CrossOrigin
public class CourseStuController extends BaseController<Course>{
	@Autowired
	private CourseService courseService;
	@Autowired
	private TeacherDao ts;
	@Autowired
	private StudentDao std;


//	@GetMapping("/course/{id}")
//	@ResponseBody
//	public Object findOneCourse(@PathVariable long id) {
//		start();
//
//		Course course = courseService.findById(id);
//		success(course);
//		return end();
//	}

	
	@GetMapping("/course/studentcourselist")
	@ResponseBody
	public Object getStudentCourseList(PageInfo<Course> pageInfo,HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
		if (account == null) {
			fail("账号不存在！");
			return end();
		}
		Long student_id = Const.STUDENT_ID;
		
		Student student = std.findById(account.getStudent().getId()).get();
		
		pageInfo.initPage(1, 8);
		Pageable pageable = PageRequest.of(pageInfo.getPageno() - 1, pageInfo.getPagesize(), pageInfo.getSort());
		Page<Course> data = courseService.findStudentCourseList(student.getId(),pageable);
		pageInfo.setReault(data);

		success(pageInfo);
		return end();
	}
	
	
}
