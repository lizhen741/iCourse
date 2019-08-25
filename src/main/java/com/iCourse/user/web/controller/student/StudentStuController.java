package com.iCourse.user.web.controller.student;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.iCourse.common.bean.Const;
import com.iCourse.common.util.BaseController;
import com.iCourse.common.util.StringUtil;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.Student;
import com.iCourse.user.service.AccountService;

@Controller
@RequestMapping("/student")
@CrossOrigin
public class StudentStuController extends BaseController<Account> {

	@Autowired
	private AccountService accountService;

	@PostMapping("/register")
	@ResponseBody
	public Object register(String mailStr,String emailCode,String password, String username, String code, HttpServletRequest req) {
		start();

		boolean returnStr = false;

		Long originalMailCode = (Long) req.getSession().getAttribute(mailStr + "check");
		String originalCaptcha = (String) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		// 1.只要有空就不许插入
		if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(mailStr) && !StringUtils.isEmpty(password)
				&& !StringUtils.isEmpty(username)) {

			// 2.判断验证码和邮箱验证码是否正确
			if (code.equalsIgnoreCase(originalCaptcha) && emailCode.equalsIgnoreCase(originalMailCode.toString())) {

				// 3.判断用户是否已注册
				Account account = accountService.findbyEmail(mailStr);
				if (account == null) {
					account = new Account();
					Student student = new Student();
					student.setStudent_name("未认证学生");
					account.setStudent(student);
					account.setEmail(mailStr);
					account.setAccount_num(mailStr);
					account.setPassword(password);
					account.setStatus(0);
					accountService.save(account);
					returnStr = true;
					success();
				}
				else
					fail("该邮箱已注册");
			}else
				fail("验证码错误");
		}else
			fail("格式错误");

		return end();
	}

	@PostMapping("/isExistance")
	@ResponseBody
	public boolean isExistance(String mailStr) {
		boolean result = false;
		if (accountService.findbyEmail(mailStr) == null)
			result = true;
		return result;
	}

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
			req.getSession().setAttribute(Const.LOGIN_STUDNET, account2);
			success(account2);
		} else {
			fail("账号或密码");
		}
		return end();
	}
	@PostMapping("/logout")
	@ResponseBody
	public Object logout(HttpServletRequest req) {
		start();
		
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
		if(account==null) {
			fail("老哥你还没登录呢");
		}
		else {
			req.getSession().setAttribute(Const.LOGIN_STUDNET, null);
			success();
		}
		return end();
	}

	@GetMapping("/isLogin")
	@ResponseBody
	public Object isLogin(HttpServletRequest req) {
		start();

		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
		if (account != null)
			success();
		else
			fail();

		return end();
	}

	@GetMapping("/getLoginStudent")
	@ResponseBody
	public Object getLoginStudent(HttpServletRequest req) {
		start();

		Account account  = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
//		Account account = accountService.findById(2l);
		successObj(account);

		return end();
	}

	@PostMapping("/settingInfo")
	@ResponseBody
	public Object settingInfo(String name, String qq, HttpServletRequest req) {
		start();
		Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);

		account.setName(name);
		account.setQq(qq);
		accountService.settingInfo(account);
		success();
		return end();
	}
	@PostMapping("/settingHeadPhoto")
	@ResponseBody
	public Object settingHeadPhoto(String head_photo,HttpServletRequest req) {
		start();
		
		if(StringUtil.isEmpty(head_photo)) {
			fail("图片不存在");
		}
		else {
			Account account = (Account) req.getSession().getAttribute(Const.LOGIN_STUDNET);
			account.setHead_photo(head_photo);
			accountService.settingHeadPhoto(account);
			success();
		}
		
		return end();
	}
}
