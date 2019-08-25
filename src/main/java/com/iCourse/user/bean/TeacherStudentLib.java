package com.iCourse.user.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name = "t_teacher_student_lib")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class TeacherStudentLib extends BaseEntity<Long>{
	private Long teacher_id;
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	public Student getStudent() {
		return student;
	}
	
	public Long getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	
	
}
