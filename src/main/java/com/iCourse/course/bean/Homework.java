package com.iCourse.course.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Table(name = "t_homework")
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Homework extends BaseEntity<Long> {
	private String homework_name;

	private Integer singlechoice_num;
	private Integer singlechoice_grade;
	private Integer multiplechoice_num;
	private Integer multiplechoice_grade;
	private Integer judgment_num;
	private Integer judgment_grade;
	private Integer completion_num;
	private Integer completion_grade;
	private Integer shortanswer_num;
	private Integer shortanswer_grade;

	private Integer homework_sum_grade;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date create_time;

	private Long course_id;
	private String chapter_ids;

	private List<Exercises> exercisesList = new ArrayList<Exercises>();

	private List<HomeworkIssued> homeworkIssueds = new ArrayList<HomeworkIssued>();
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	public List<Exercises> getExercisesList() {
		return exercisesList;
	}

	
	@OneToMany(mappedBy="homework",cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	public List<HomeworkIssued> getHomeworkIssueds() {
		return homeworkIssueds;
	}





	public void setHomeworkIssueds(List<HomeworkIssued> homeworkIssueds) {
		this.homeworkIssueds = homeworkIssueds;
	}





	public String getHomework_name() {
		return homework_name;
	}

	public void setHomework_name(String homework_name) {
		this.homework_name = homework_name;
	}

	public Integer getSinglechoice_num() {
		return singlechoice_num;
	}

	public String getChapter_ids() {
		return chapter_ids;
	}

	public void setChapter_ids(String chapter_ids) {
		this.chapter_ids = chapter_ids;
	}

	public void setSinglechoice_num(Integer singlechoice_num) {
		this.singlechoice_num = singlechoice_num;
	}

	public Integer getSinglechoice_grade() {
		return singlechoice_grade;
	}

	public void setSinglechoice_grade(Integer singlechoice_grade) {
		this.singlechoice_grade = singlechoice_grade;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Integer getMultiplechoice_num() {
		return multiplechoice_num;
	}

	public void setMultiplechoice_num(Integer multiplechoice_num) {
		this.multiplechoice_num = multiplechoice_num;
	}

	public Integer getMultiplechoice_grade() {
		return multiplechoice_grade;
	}

	public void setMultiplechoice_grade(Integer multiplechoice_grade) {
		this.multiplechoice_grade = multiplechoice_grade;
	}

	public Integer getJudgment_num() {
		return judgment_num;
	}

	public void setJudgment_num(Integer judgment_num) {
		this.judgment_num = judgment_num;
	}

	public Integer getJudgment_grade() {
		return judgment_grade;
	}

	public void setJudgment_grade(Integer judgment_grade) {
		this.judgment_grade = judgment_grade;
	}

	public Integer getCompletion_num() {
		return completion_num;
	}

	public void setCompletion_num(Integer completion_num) {
		this.completion_num = completion_num;
	}

	public Integer getCompletion_grade() {
		return completion_grade;
	}

	public void setCompletion_grade(Integer completion_grade) {
		this.completion_grade = completion_grade;
	}

	public Integer getShortanswer_num() {
		return shortanswer_num;
	}

	public void setShortanswer_num(Integer shortanswer_num) {
		this.shortanswer_num = shortanswer_num;
	}

	public Integer getShortanswer_grade() {
		return shortanswer_grade;
	}

	public void setShortanswer_grade(Integer shortanswer_grade) {
		this.shortanswer_grade = shortanswer_grade;
	}

	public Integer getHomework_sum_grade() {
		return homework_sum_grade;
	}

	public void setHomework_sum_grade(Integer homework_sum_grade) {
		this.homework_sum_grade = homework_sum_grade;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public void setExercisesList(List<Exercises> exercisesList) {
		this.exercisesList = exercisesList;
	}

}
