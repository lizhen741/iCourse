package com.iCourse.course.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Table(name="t_homeworkissued_link_clazz")
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class HomeworkIssuedLinkClazz extends BaseEntity<Long>{

	private HomeworkIssued homeworkIssued;
	private Long homeworkIssuedId;
	private Clazz clazz;
	private Long clazzId;
	
	private List<StudentHomework> studentHomeworks = new ArrayList<StudentHomework>();

	@ManyToOne
	@JoinColumn(name="homeworkIssued_id")
	@JsonIgnore
	public HomeworkIssued getHomeworkIssued() {
		return homeworkIssued;
	}
	
	@ManyToOne
	@JoinColumn(name="clazz_id")
	public Clazz getClazz() {
		return clazz;
	}

	@OneToMany(mappedBy="homeworkissued_link_clazz",cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JsonIgnore
//	@JSONField(serialize = false)
	public List<StudentHomework> getStudentHomeworks() {
		return studentHomeworks;
	}

	public Long getHomeworkIssuedId() {
		return homeworkIssuedId;
	}

	public void setHomeworkIssuedId(Long homeworkIssuedId) {
		this.homeworkIssuedId = homeworkIssuedId;
	}

	public void setHomeworkIssued(HomeworkIssued homeworkIssued) {
		this.homeworkIssued = homeworkIssued;
	}

	
	public void setStudentHomeworks(List<StudentHomework> studentHomeworks) {
		this.studentHomeworks = studentHomeworks;
	}


	
	public Long getClazzId() {
		return clazzId;
	}

	public void setClazzId(Long clazzId) {
		this.clazzId = clazzId;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}


	
}
