package com.iCourse.course.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;
import com.iCourse.user.bean.Student;

@Entity
@Table(name = "t_clazz")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Clazz extends BaseEntity<Long> implements Serializable {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date clazz_begin;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date clazz_end;
	
	
	private String clazz_name;
	private Boolean clazz_status;
	private Boolean clazz_syn;
	private String clazz_serial_number; // 课堂序列号 用于学生加入课堂
	private Integer clazz_student_number;
	private Course course;
	private List<Student> students = new ArrayList<Student>();

	@JsonBackReference
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "t_clazz_student", joinColumns = @JoinColumn(name = "clazz_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Date getClazz_begin() {
		return clazz_begin;
	}

	public Date getClazz_end() {
		return clazz_end;
	}

	@ManyToOne
	@JoinColumn(name = "course_id")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setClazz_begin(Date clazz_begin) {
		this.clazz_begin = clazz_begin;
	}

	public void setClazz_end(Date clazz_end) {
		this.clazz_end = clazz_end;
	}

	public String getClazz_name() {
		return clazz_name;
	}

	public void setClazz_name(String clazz_name) {
		this.clazz_name = clazz_name;
	}

	public Boolean getClazz_status() {
		return clazz_status;
	}

	public void setClazz_status(Boolean clazz_status) {
		this.clazz_status = clazz_status;
	}

	public Boolean getClazz_syn() {
		return clazz_syn;
	}

	public void setClazz_syn(Boolean clazz_syn) {
		this.clazz_syn = clazz_syn;
	}

	public String getClazz_serial_number() {
		return clazz_serial_number;
	}

	public void setClazz_serial_number(String clazz_serial_number) {
		this.clazz_serial_number = clazz_serial_number;
	}

	public Integer getClazz_student_number() {
		return clazz_student_number;
	}

	public void setClazz_student_number(Integer clazz_student_number) {
		this.clazz_student_number = clazz_student_number;
	}

	public Clazz() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Clazz(Long id2, String clazz_name) {
		super(id2);
		this.clazz_name = clazz_name;
	}

	
}
