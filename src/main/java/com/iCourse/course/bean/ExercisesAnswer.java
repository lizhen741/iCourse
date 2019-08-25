package com.iCourse.course.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name = "t_exercises_answer")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ExercisesAnswer extends BaseEntity<Long>{
	private String short_answer; // 简答题或填空题答案或选择题答案
	private Boolean judge_answer; // 判断题答案
	private String answer_explain;
	private Exercises exercises;
	
	@OneToOne(mappedBy = "exercisesAnswer")
	@JsonIgnore
	public Exercises getExercises() {
		return exercises;
	}

	public void setExercises(Exercises exercises) {
		this.exercises = exercises;
	}

	public String getShort_answer() {
		return short_answer;
	}

	public void setShort_answer(String short_answer) {
		this.short_answer = short_answer;
	}

	public Boolean getJudge_answer() {
		return judge_answer;
	}

	public void setJudge_answer(Boolean judge_answer) {
		this.judge_answer = judge_answer;
	}

	public String getAnswer_explain() {
		return answer_explain;
	}

	public void setAnswer_explain(String answer_explain) {
		this.answer_explain = answer_explain;
	}

	@Override
	public String toString() {
		return "ExercisesAnswer [short_answer=" + short_answer + ", judge_answer=" + judge_answer + ", answer_explain="
				+ answer_explain + "]";
	}


}
