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

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;
import com.iCourse.user.bean.Student;

@Table(name="t_studenthomework")
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class StudentHomework extends BaseEntity<Long> {

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date finish_time;
	private Integer sum_grade;
	private String status;
	private Long HLC_id;

	private HomeworkIssuedLinkClazz homeworkissued_link_clazz;
	
	private Student student;
	private List<StudentExercisesAnswerRecord> student_answers = new ArrayList<StudentExercisesAnswerRecord>();

	@ManyToOne
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return student;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn
//	@JsonIgnore
//	@JSONField(serialize = false)
	public HomeworkIssuedLinkClazz getHomeworkissued_link_clazz() {
		return homeworkissued_link_clazz;
	}

	

	@OneToMany(mappedBy="studentHomework",fetch=FetchType.EAGER)
	@JsonIgnore
	public List<StudentExercisesAnswerRecord> getStudent_answers() {
		return student_answers;
	}

	


	public void setHomeworkissued_link_clazz(HomeworkIssuedLinkClazz homeworkissued_link_clazz) {
		this.homeworkissued_link_clazz = homeworkissued_link_clazz;
	}

	public void setStudent_answers(List<StudentExercisesAnswerRecord> student_answers) {
		this.student_answers = student_answers;
	}

	

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getSum_grade() {
		return sum_grade;
	}

	public void setSum_grade(Integer sum_grade) {
		this.sum_grade = sum_grade;
	}

	public Long getHLC_id() {
		return HLC_id;
	}

	public void setHLC_id(Long hLC_id) {
		HLC_id = hLC_id;
	}

}
