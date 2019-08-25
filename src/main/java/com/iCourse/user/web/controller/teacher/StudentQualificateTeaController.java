package com.iCourse.user.web.controller.teacher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.Const;
import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.StuQualificate;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.bean.DTO.StuQualificateQueryDTO;
import com.iCourse.user.service.StuQualificateService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class StudentQualificateTeaController extends BaseController<StuQualificate> {
	@Autowired
	private StuQualificateService stuQualificateService;

	@PostMapping("/getStudentQualificates")
	@ResponseBody
	public Object getStudentQualificates(StuQualificateQueryDTO query, PageInfo<StuQualificate> pageInfo,
			HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		
		query.setTeacher_id(account.getTeacher().getId());
		pageInfo.initPage(1, 8);

		Page<StuQualificate> data = stuQualificateService.findStudentQualificate(query.getWhere(query),pageInfo.getPageable());

		pageInfo.setReault(data);
		success(pageInfo);

		return end();
	}
	
	@PostMapping("/passStudentQualificate/{sq_id}")
	@ResponseBody
	public Object passStudentQualificate(@PathVariable Long sq_id,HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
//		Teacher teacher = new Teacher();
//		teacher.setId(1l);
		Integer result = stuQualificateService.passStudentQualificate(sq_id,account.getTeacher().getId());
		
		if(result==-1) {
			fail("该学生认证不存在");
		}
		else if(result==0) {
			fail("别搞事！该学生认证与你无关");
		}
		else if(result==1) {
			success();
		}
			
		return end();
	}

	@PostMapping("/failStudentQualificate/{sq_id}")
	@ResponseBody
	public Object failStudentQualificate(@PathVariable Long sq_id,HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
//		Teacher teacher = new Teacher();
//		teacher.setId(1l);
		Integer result = stuQualificateService.failStudentQualificate(sq_id,account.getTeacher().getId());
		
		if(result==-1) {
			fail("该学生认证不存在");
		}
		else if(result==0) {
			fail("别搞事！该学生认证与你无关");
		}
		else if(result==1) {
			success();
		}
			
		return end();
	}
}
