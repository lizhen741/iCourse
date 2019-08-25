package com.iCourse.user.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iCourse.common.bean.BaseEntity;
import com.iCourse.course.bean.Course;


@Entity
@Table(name="t_teacher")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Teacher extends BaseEntity<Long> implements Serializable{

	private String teacher_name;
	private String teacher_email;
	private String teacher_num;
	private Boolean open_qualificate_student;
	private TeachCertificate certificate;
	
	private List<Course> courses = new ArrayList<Course>();
	
	
	@OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonBackReference
	public List<Course> getCourses() {
		return courses;
	}
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonManagedReference
	public TeachCertificate getCertificate() {
		return certificate;
	}

	
	public void setCertificate(TeachCertificate certificate) {
		this.certificate = certificate;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public String getTeacher_email() {
		return teacher_email;
	}

	public void setTeacher_email(String teacher_email) {
		this.teacher_email = teacher_email;
	}


	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public String getTeacher_num() {
		return teacher_num;
	}
	public void setTeacher_num(String teacher_num) {
		this.teacher_num = teacher_num;
	}
	public Boolean getOpen_qualificate_student() {
		return open_qualificate_student;
	}
	public void setOpen_qualificate_student(Boolean open_qualificate_student) {
		this.open_qualificate_student = open_qualificate_student;
	}

	
	
}
