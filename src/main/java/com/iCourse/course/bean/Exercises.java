package com.iCourse.course.bean;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name = "t_exercises")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Exercises extends BaseEntity<Long> {
	private String exercises_content;
	private String exercises_type;
	private Integer difficulty;
	private Long course_id;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date create_time;
	

	
	private ExercisesAnswer exercisesAnswer;
	private List<Chapter> chapters = new ArrayList<Chapter>(); // 题目相关知识点
	private List<ExercisesItem> exercisesItems = new ArrayList<ExercisesItem>(); // 选择题选项

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<Chapter> getChapters() {
		return chapters;
	}

	@OneToMany(mappedBy="exercises",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	public List<ExercisesItem> getExercisesItems() {
		return exercisesItems;
	}


//	@Transient
//	public List<String> getChoiceAnswer() {
//		return choiceAnswer;
//	}
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn
	public ExercisesAnswer getExercisesAnswer() {
		return exercisesAnswer;
	}

	public void setExercisesAnswer(ExercisesAnswer exercisesAnswer) {
		this.exercisesAnswer = exercisesAnswer;
	}

	
	

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Exercises() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getExercises_content() {
		return exercises_content;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public void setExercises_content(String exercises_content) {
		this.exercises_content = exercises_content;
	}

	public String getExercises_type() {
		return exercises_type;
	}


	public void setExercisesItems(List<ExercisesItem> exercisesItems) {
		this.exercisesItems = exercisesItems;
	}

	public void setExercises_type(String exercises_type) {
		this.exercises_type = exercises_type;
	}

	

//	public void setChoiceAnswer(List<String> choiceAnswer) {
//		this.choiceAnswer = choiceAnswer;
//	}



	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}




	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return "Exercises [exercises_content=" + exercises_content + ", exercisesAnswer=" + exercisesAnswer
				+ ", exercisesItems=" + exercisesItems + "]";
	}



	
	
}
