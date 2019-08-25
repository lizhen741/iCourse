package com.iCourse.course.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name = "t_exercises_item")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ExercisesItem extends BaseEntity<Long>{

	private String item_content; 
	private String item_index;
	private Exercises exercises;

	@ManyToOne
	@JoinColumn(name="exercises_id")
	@JsonIgnore
	public Exercises getExercises() {
		return exercises;
	}
	
	
	public ExercisesItem() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ExercisesItem(Long id2, String item_content) {
		super(id2);
		this.item_content = item_content;
	}


	public String getItem_index() {
		return item_index;
	}


	public void setItem_index(String item_index) {
		this.item_index = item_index;
	}


	public String getItem_content() {
		return item_content;
	}

	public void setItem_content(String item_content) {
		this.item_content = item_content;
	}

	

	public void setExercises(Exercises exercises) {
		this.exercises = exercises;
	}


	@Override
	public String toString() {
		return "ExercisesItem [item_content=" + item_content + ", item_index=" + item_index + "]";
	}
	
	
}
