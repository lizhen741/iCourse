package com.iCourse.user.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Mail {
	
	private String sourceAddress;
	private String destinationAddress;
	private String subject;
	private String sendDate;
	private String Test;
	private Long randomNum;
	public Long getRandomNum() {
		return randomNum;
	}
	public void setRandomNum(Long randomNum) {
		this.randomNum = randomNum;
	}
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getTest() {
		return Test;
	}
	public void setTest(String test) {
		Test = test;
	}
	
}
