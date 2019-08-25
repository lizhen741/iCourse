package com.iCourse.course.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StudentHomeworkPack {
	private Long homework_id;
	private Long stuHomework_id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date begin_time;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date end_time;
	
	private String status;
	
	private String homework_name;
	private Integer grade;
	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getHomework_name() {
		return homework_name;
	}
	public void setHomework_name(String homework_name) {
		this.homework_name = homework_name;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Long getHomework_id() {
		return homework_id;
	}
	public void setHomework_id(Long homework_id) {
		this.homework_id = homework_id;
	}
	public Long getStuHomework_id() {
		return stuHomework_id;
	}
	public void setStuHomework_id(Long stuHomework_id) {
		this.stuHomework_id = stuHomework_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}
