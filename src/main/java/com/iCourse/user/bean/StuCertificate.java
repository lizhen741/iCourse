package com.iCourse.user.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name="t_stu_certificate")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class StuCertificate extends BaseEntity<Long> implements Serializable{
	private String name;
	private String school;
	private String clazzname;
	private String speciality;
	private String grade;
	private String education;
	private String student_num;
	private Student student;
	
	@OneToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JsonBackReference
	public Student getStudent() {
		return student;
	}

	public String getName() {
		return name;
	}

	public String getSchool() {
		return school;
	}

	public String getClazzname() {
		return clazzname;
	}

	public String getEducation() {
		return education;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setClazzname(String clazzname) {
		this.clazzname = clazzname;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getStudent_num() {
		return student_num;
	}

	public void setStudent_num(String student_num) {
		this.student_num = student_num;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	
	
	
	
	
}
