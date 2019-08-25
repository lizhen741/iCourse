package com.iCourse.Mail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.iCourse.BaseTest;
import com.iCourse.user.bean.Mail;
import com.iCourse.user.service.MailService;

public class TestMail extends BaseTest {

	@Autowired
	private MailService mailService;
	
	//@Test
	public void sendMail() {
		
		Mail mail = new Mail();
		mail.setSourceAddress("hejincai_email@163.com");
		mail.setDestinationAddress("hejincai_email@163.com");
		mail.setSubject("测试");
		long max = 2000l;
        long min = 1000l;
        Random random = new Random();
        Long s = random.nextLong()%(max-min+1l) + min;
		mail.setTest(s+"");
		
		mailService.sendEMail(mail);
	}
	
	//@Test
	public void dateFormat() {
		Date date = new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dateFormat.format(date);
		try {
			System.out.println("\n");
			System.out.println(str);
			System.out.println(dateFormat.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void isOutofdeafTime() {
		String str = "2019-07-05 11:08:56";
		System.out.println(mailService.isOutofdeafTime(str));
	}
	
}
