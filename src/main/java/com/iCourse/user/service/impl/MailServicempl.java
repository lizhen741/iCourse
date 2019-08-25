package com.iCourse.user.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iCourse.user.bean.Mail;
import com.iCourse.user.bean.MailConfiguration;
import com.iCourse.user.service.MailService;

@Service
@Transactional
public class MailServicempl implements MailService {
	
	/**
	 * 发送邮件
	 * return产生的随机数
	 */
	public Mail sendEMail(Mail mail) {
		MailConfiguration mailConfiguration = new MailConfiguration();
		JavaMailSenderImpl mailSender = mailConfiguration.JavaMailSender();
		MimeMessage message = mailSender.createMimeMessage();
		Long randomNum;
		try {

			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(mail.getSourceAddress());
			helper.setTo(mail.getDestinationAddress());
			helper.setSubject(mail.getSubject());
			Date date = new Date();
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd-FF HH:mm:ss");
			String str = dateFormat.format(date);
			mail.setSendDate(str);
	        Random random = new Random();
	        randomNum = random.nextLong()%(20000l-10000l+1l) + 10000l;
			String mailTest = "您好，您在iCourse注册的邮箱验证码为："+randomNum+"，24小时内有效";
			mail.setRandomNum(randomNum);
			mail.setTest(mailTest);
			
			helper.setText(mail.getTest());

		} catch (MessagingException e) {
			throw new MailParseException(e);
		}

		mailSender.send(message);
		return mail;
	}
	
	//判断邮箱验证码是否过期,beginStr为发送邮件时的日期字符串
	public boolean isOutofdeafTime(String beginStr) {
		Date now = new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endStr = dateFormat.format(now);
		Date beginTime = null;
		Date endTime = null;
		
		try {
			beginTime = dateFormat.parse(beginStr);
			endTime = dateFormat.parse(endStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long temp = (((endTime.getTime()-beginTime.getTime())/1000)/60)/60;
		return (temp<=24 && temp>0)?true:false;
	}
}
