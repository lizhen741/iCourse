package com.iCourse.course.web.controller.student;

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
import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.DTO.ClazzQueryDTO;
import com.iCourse.course.service.ClazzService;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.Student;
import com.iCourse.user.dao.StudentDao;

@Controller
@RequestMapping("/student")
@CrossOrigin
public class ClazzStuController extends BaseController<Clazz>{
	@Autowired
	private ClazzService clazzService;
	@Autowired
	private StudentDao studentDao;


	@GetMapping("/getClazzBySerial/{serial}")
	@ResponseBody
	public Object getClazz(@PathVariable String serial) {
		
		Clazz clazz = clazzService.findBySerail(serial);
		
		return clazz;
	}
	
	@PostMapping("/joinClazz/{clazz_id}")
	@ResponseBody
	public Object joinClazz(@PathVariable Long clazz_id,HttpServletRequest req ) {
		start();
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
		if (account == null) {
			fail("账号不存在！");
			return end();
		}
//		Long student_id = Const.STUDENT_ID;
		
		Student student = studentDao.findById(account.getStudent().getId()).get();
		clazzService.joinClazz(clazz_id,student);
		success();
		return end();
		
	}
	
	@PostMapping("/getClazzList")
	@ResponseBody
	public Object getClazzList(ClazzQueryDTO query,PageInfo<Clazz> pageInfo,HttpServletRequest req) {
		start();
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
		
		if (account == null) {
			fail("账号不存在！");
			return end();
		}
//		Long student_id = Const.STUDENT_ID;
		
		query.setStudent_id(account.getStudent().getId());
		pageInfo.initPage(1, 8);
		Page<Clazz> data =  clazzService.getClazzesByStudentSpec(query.getWhere(query),pageInfo.getPageable());
		pageInfo.setReault(data);
		success(pageInfo);
		
		return end();
		
	}

	
	

}
