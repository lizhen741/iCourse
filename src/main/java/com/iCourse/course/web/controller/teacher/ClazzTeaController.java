package com.iCourse.course.web.controller.teacher;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.Const;
import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.common.util.RandomString;
import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.ClazzQueryDTO;
import com.iCourse.course.service.ClazzService;
import com.iCourse.course.service.CourseService;
import com.iCourse.user.bean.Account;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class ClazzTeaController extends BaseController<Clazz> {
	@Autowired
	private ClazzService clazzService;
	@Autowired
	private CourseService courseService;

	/**
	 * 为某课程创建一个上课课堂
	 * 
	 * @param clazz
	 * @param course_id
	 * @param req
	 * @return
	 */
	@PostMapping("/clazz")
	@ResponseBody
	public Object add(Clazz clazz, Long course_id, HttpServletRequest req) {
		start();

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);

		/*
		 * 登录修改 Course course = courseService.findById(course_id);
		 */
		Course course = courseService.findById(account.getTeacher().getId());
		//if (account.getTeacher().getId() != course.getTeacher().getId()) {
		if (course.getTeacher().getId()!=account.getTeacher().getId()) {
			fail("你与该课程无关，不能添加课堂");
		} else {
			clazz.setClazz_student_number(0);
			clazz.setClazz_serial_number(RandomString.getRandomString(5));

			clazzService.save(clazz, course_id);
			success();
		}

		return end();
	}

	/**
	 * 修改课堂信息
	 * 
	 * @param clazz
	 * @return
	 */
	@PostMapping("/editClazz")
	@ResponseBody
	public String updateClazz(Clazz clazz) {
//		Chapter chapter = new Chapter();
//		chapter.setChapter_name(chapter_name);
//		chapter.setId(id);
		clazzService.update(clazz);
		return "ojbk";
	}

	/**
	 * 获取某个课程的课堂列表
	 * 
	 * @param query
	 * @param pageInfo
	 * @return
	 */
	@GetMapping("/clazz")
	@ResponseBody
	public Object getClazz(ClazzQueryDTO query, PageInfo<Clazz> pageInfo, HttpServletRequest req) {
		start();

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		/*
		 * Course course = courseService.findById(query.getCourse_id());
		 */
		Course course = courseService.findById(account.getTeacher().getId());
//		if (account.getTeacher().getId() != course.getTeacher().getId()) {
		if (course.getTeacher().getId()!=account.getTeacher().getId()) {
			fail("你与该课程无关，不能添加课堂");
		} else {

			pageInfo.initPage(1, 8);

			Pageable pageable = PageRequest.of(pageInfo.getPageno() - 1, pageInfo.getPagesize(), pageInfo.getSort());
			Page<Clazz> data = clazzService.findByCourse(query.getWhere(query), pageable);
			pageInfo.setReault(data);

			success(pageInfo);
		}

		return end();
	}

	/**
	 * 添加批量添加学生到课堂
	 * 
	 * @param clazz
	 * @param ids
	 * @return
	 */
	@PostMapping("/clazz/addstudents")
	@ResponseBody
	public Object clazzAddStudents(Clazz clazz, @RequestParam(value = "ids[]") List<Long> ids) {
		start();

		int flag = clazzService.clazzAddStudents(clazz, ids);

		success();
		return end();
	}

	/**
	 * 删除课堂学生
	 * 
	 * @param clazz_id
	 * @param ids
	 * @return
	 */
	@PostMapping("/clazz/{clazz_id}/delstudents")
	@ResponseBody
	public Object clazzDelStudents(@PathVariable Long clazz_id, @RequestParam(value = "ids[]") Long[] ids) {
		start();

		int flag = clazzService.clazzDelStudents(clazz_id, ids);

		success();
		return end();
	}

	/**
	 * 获取课堂，以便布置作业
	 * 
	 * @param course_id
	 * @return
	 */
	@GetMapping("/getClazzesForHomework")
	@ResponseBody
	public Object getClazzesForHomework(Long course_id) {
		start();

		List<Clazz> clazzes = clazzService.getClazzesForHomework(course_id);

		success(clazzes);

		return end();
	}
	
	/**
	 * 获取某个课堂所有的学生id
	 * @param clazz_id
	 * @param req
	 * @return
	 */
	@GetMapping("/getClazzAllStudentId")
	@ResponseBody
	public Object getClazzAllStudentId(Long clazz_id,HttpServletRequest req) {
		start();
		
		List<Long> ids = clazzService.getClazzAllStudentId(clazz_id);
		successObj(ids);
		
		return end();
	}

}
