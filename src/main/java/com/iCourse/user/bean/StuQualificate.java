package com.iCourse.user.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name="t_stuqualificate")
public class StuQualificate extends BaseEntity<Long>{
	private Student student;
	private StuCertificate stuCertificate;
	private Teacher teacher;
	private Boolean pass;
	private Boolean isCheck;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date create_time;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date pass_time;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date fail_time;

	@OneToOne(cascade= {CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.EAGER)
	@JoinColumn(name="student_id")
	public Student getStudent() {
		return student;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}

	@OneToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.EAGER)
	public StuCertificate getStuCertificate() {
		return stuCertificate;
	}



	
	public void setStuCertificate(StuCertificate stuCertificate) {
		this.stuCertificate = stuCertificate;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public Date getPass_time() {
		return pass_time;
	}

	public void setPass_time(Date pass_time) {
		this.pass_time = pass_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}

	public Date getFail_time() {
		return fail_time;
	}

	public void setFail_time(Date fail_time) {
		this.fail_time = fail_time;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	} 
	
	
}
