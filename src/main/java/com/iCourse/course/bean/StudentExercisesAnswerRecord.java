package com.iCourse.course.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Table(name="t_student_exercises_answer_record")
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class StudentExercisesAnswerRecord extends BaseEntity<Long>{
	private Long exercises_id;
	
	private String answer;
	private Integer grade;         //学生得分
	private Integer max_grade;      //题目分值
	private Boolean isright;
	
	private StudentHomework studentHomework;

	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name = "studentHomework_id")
	public StudentHomework getStudentHomework() {
		return studentHomework;
	}

	public void setStudentHomework(StudentHomework studentHomework) {
		this.studentHomework = studentHomework;
	}

	public Long getExercises_id() {
		return exercises_id;
	}

	public void setExercises_id(Long exercises_id) {
		this.exercises_id = exercises_id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Boolean getIsright() {
		return isright;
	}

	public void setIsright(Boolean isright) {
		this.isright = isright;
	}

	public Integer getMax_grade() {
		return max_grade;
	}

	public void setMax_grade(Integer max_grade) {
		this.max_grade = max_grade;
	}
	

}
