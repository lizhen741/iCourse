package com.iCourse.course.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Table(name = "t_homeworkissued")
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class HomeworkIssued extends BaseEntity<Long>{

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date begin_time;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date end_time;
	
	private String status;
	
	private Homework homework;
	private List<HomeworkIssuedLinkClazz> homeworkIssuedLinkClazzes = new ArrayList<HomeworkIssuedLinkClazz>();
	
	
	
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name = "homework_id")
	public Homework getHomework() {
		return homework;
	}
	
	@OneToMany(mappedBy="homeworkIssued",cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
//	@JsonIgnore
	public List<HomeworkIssuedLinkClazz> getHomeworkIssuedLinkClazzes() {
		return homeworkIssuedLinkClazzes;
	}

	public void setHomeworkIssuedLinkClazzes(List<HomeworkIssuedLinkClazz> homeworkIssuedLinkClazzes) {
		this.homeworkIssuedLinkClazzes = homeworkIssuedLinkClazzes;
	}

	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	
	public void setHomework(Homework homework) {
		this.homework = homework;
	}
	
	
	
	
}
