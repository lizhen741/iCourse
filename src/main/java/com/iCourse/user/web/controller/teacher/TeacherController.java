package com.iCourse.user.web.controller.teacher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.Const;
import com.iCourse.common.util.BaseController;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.service.AccountService;
import com.iCourse.user.service.TeacherService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class TeacherController extends BaseController<Teacher>{
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/login")
	@ResponseBody
	public Object login(Account account,HttpServletRequest req) {
		start();
		
		Account account2 = accountService.login(account);
		if (account2 == null) {
			fail("账号不存在！");
		} else if (account2.getStatus() == -1) {
			fail("账号已停用！");
		} else if (account2.getPassword().equals(account.getPassword())) {
			account2.setPassword(null);
			req.getSession().setAttribute(Const.LOGIN_TEACHER, account2);
			successObj(account2);
		} else {
			fail("账号或密码");
		}
		
		return end();
	}
	
	@PostMapping("/logout")
	@ResponseBody
	public Object logout(HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		if(account==null) {
			fail("老哥你还没登录呢");
		}
		else {
			req.getSession().setAttribute(Const.LOGIN_TEACHER, null);
			success();
		}
		return end();
	}
	
	@GetMapping("/islogin")
	@ResponseBody
	public Object isLogin(HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		if (account != null)
			successObj(account);
		else
			fail();
		
		
		return end();
	}
}
