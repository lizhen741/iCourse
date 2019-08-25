package com.iCourse.user.web.controller.common;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 邮件相关服务
 * @author pc
 *
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.user.bean.Mail;
import com.iCourse.user.service.MailService;
@Controller
@RequestMapping(value = "/mail")
@CrossOrigin
public class MailController {
	
	@Autowired
	private MailService mailService; 
	
	@PostMapping("/sendEmail")
	@ResponseBody
	public boolean sendEmail(String mailStr,HttpServletRequest req) {
		boolean resultStr = false;
		if(!StringUtils.isEmpty(mailStr)) {
			Mail mail = new Mail();
			mail.setSourceAddress("2248005433@qq.com");
			mail.setDestinationAddress(mailStr);
			mail.setSubject("iCourse注册提醒");
			long max = 2000l;
	        long min = 1000l;
	        Random random = new Random();
	        Long s = random.nextLong()%(max-min+1l) + min;
			mail.setTest(s+"");
			
			Mail result = mailService.sendEMail(mail);
			//将为当前邮箱产生的随机数存入session
			req.getSession().setAttribute(mailStr+"check", result.getRandomNum());
			
			resultStr = true;
		}
		return resultStr;
	}
}
