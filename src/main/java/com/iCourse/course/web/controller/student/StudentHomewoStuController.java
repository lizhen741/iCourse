package com.iCourse.course.web.controller.student;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.bean.StudentHomework;
import com.iCourse.course.service.HomeworkService;
import com.iCourse.course.service.StudentHomeworkService;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.Student;

@Controller
@RequestMapping("/studentHomework")
@CrossOrigin
public class StudentHomewoStuController extends BaseController<StudentHomework> {
	@Autowired
	private StudentHomeworkService studentHomeworkService;
	@Autowired
	private HomeworkService homeworkService;


	/**
	 * 学生获取他的某个课堂的作业列表
	 * @param clazz_id
	 * @param pageInfo
	 * @param req
	 * @return
	 */
	@PostMapping("/getList")
	@ResponseBody
	public Object getStudentHomeworkList(Long clazz_id, PageInfo<StudentHomework> pageInfo, HttpServletRequest req) {
		start();
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
		if (account == null) {
			fail("未登录！");
			return end();
		}
		
//		Long student_id = Const.STUDENT_ID;
		
		Student student = account.getStudent();
//		student.setId(student_id);

		pageInfo.initPage(1, 8);
		Map<String,Object> map = studentHomeworkService.findStudentHomeworksByClazzId(student.getId(),
				clazz_id,pageInfo.getPageable());
		
		pageInfo.setReault((Page<StudentHomework>) map.get("pageable"));
		pageInfo.setDatas(null);
		success(pageInfo);
		successObjList((List<Object>)map.get("data"));
		
		return end();
	}
	
	
	/**
	 * 学生查看某个具体作业结果时调用
	 * @param homework_id
	 * @return
	 */
	@GetMapping("/getHomeworkResult")
	@ResponseBody
	public Object getHomeworkResult(Long homework_id) {
		start();
		
		Homework homework = homeworkService.getHomework(homework_id);
		
		for (Exercises exercises : homework.getExercisesList()) {
			System.out.println(exercises.getId());
		}
		success();
		
		return homework;
	}
	
	
	/**
	 * 学生打开某个具体作业作答时调用
	 * @param homework_id
	 * @return
	 */
	@GetMapping("/getHomework")
	@ResponseBody
	public Object getHomework(Long homework_id) {
		start();
		Homework homework = homeworkService.getHomework(homework_id);
		for (Exercises exercises : homework.getExercisesList()) {
			exercises.setChapters(null);
			exercises.setExercisesAnswer(null);
		}
		success();
		
		return homework;
	}
	
	/**
	 * 学生打开某个具体作业作答时调用
	 * @param stuHomework_id
	 * @return
	 */
	@GetMapping("/getStudentHomework")
	@ResponseBody
	public Object getStudentHomework(Long stuHomework_id) {
		start();
		
		StudentHomework studentHomework = studentHomeworkService.findById(stuHomework_id);
		studentHomework.setStudent(null);
		studentHomework.setHomeworkissued_link_clazz(null);
		success(studentHomework);
		
		return end();
	}
	
	/**
	 * 学生作业记录
	 * @param stuHwRecord_id
	 * @param newAnswer
	 * @return
	 */
	@PostMapping("/updateAnswer/{stuHwRecord_id}")
	@ResponseBody
	public Object updateAnswer(@PathVariable Long stuHwRecord_id,String newAnswer) {
		start();
		
		studentHomeworkService.updateAnswer(stuHwRecord_id,newAnswer);
		
		success();
		return end();
	}
	
	/**
	 * 学生提交作业
	 * @param stuHomework_id
	 * @param req
	 * @return
	 */
	@PostMapping("/submitHomework/{stuHomework_id}")
	@ResponseBody
	public Object submitHomework(@PathVariable Long stuHomework_id,HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
		if (account == null) {
			fail("账号不存在！");
			return end();
		}
//		Long student_id = Const.STUDENT_ID;
		studentHomeworkService.studentFinishHomework(stuHomework_id,account.getStudent().getId());
		
		success();
		return end();
	}
	
	@GetMapping("/getEndTime")
	@ResponseBody
	public Object getHomeworkEndTime(Long stuHomework_id) {
		start();
		
		Date time = studentHomeworkService.getHomeworkEndTime(stuHomework_id);
		
		return time;
	}
}
