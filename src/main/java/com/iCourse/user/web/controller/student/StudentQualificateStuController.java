package com.iCourse.user.web.controller.student;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.Const;
import com.iCourse.common.util.BaseController;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.StuQualificate;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.service.StuQualificateService;
import com.iCourse.user.service.TeacherService;

@Controller
@RequestMapping("/student")
@CrossOrigin
public class StudentQualificateStuController extends BaseController<StuQualificate>{
	@Autowired
	private StuQualificateService stuQualificateService;
	@Autowired
	private TeacherService teacherService;
	
	@PostMapping("/stuQualificate")
	@ResponseBody
	public Object  studentQualificate(@RequestBody StuQualificate stuQualificate,HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
//		Student student = account.getStudent();
//		Student student = new Student();
//		student.setId(24l);
		
		if(stuQualificate.getTeacher()==null) {
			fail();
		}	
		else {
			stuQualificate.setCreate_time(new Date());
			stuQualificate.setIsCheck(false);
			stuQualificate.setStudent(account.getStudent());
			stuQualificateService.createQualificate(stuQualificate);
			success();
		}
			
		
		return end();
	}
	@GetMapping("/getQualificateTeacher")
	@ResponseBody
	public Object getQualificateTeacher(String teacher_num) {
		start();
		
		Teacher teacher = teacherService.findById(teacher_num);
		successObj(teacher);
		
		return end();
	}
}
