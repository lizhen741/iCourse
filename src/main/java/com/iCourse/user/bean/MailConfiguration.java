package com.iCourse.user.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

	@Bean
    public JavaMailSenderImpl JavaMailSender(){
		
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("2248005433@qq.com");
        mailSender.setPassword("mfnacdicpsppebfd");
        mailSender.setPort(25);
        return  mailSender;
    }

}
