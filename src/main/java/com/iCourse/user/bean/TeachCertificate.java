package com.iCourse.user.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name="t_teach_certificate")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class TeachCertificate extends BaseEntity<Long> implements Serializable{
	private String name;
	private String school;
	private String speciality;
	private String picture;
	private Teacher teacher;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JsonBackReference
	public Teacher getTeacher() {
		return teacher;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}

	public String getSpeciality() {
		return speciality;
	}


	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}


	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	
	
}
