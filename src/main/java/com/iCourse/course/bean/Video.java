package com.iCourse.course.bean;

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
@Table(name = "t_video")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Video extends BaseEntity<Long> implements Serializable{
	private String video_name;
	private String video_src;
	private Long course_id;
	private Long chapter_id;
	private Chapter chapter;
	

	
	@OneToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="chapter_key")//为解决双向绑定两方都无法删除，这个字段会为空
	@JsonBackReference
	public Chapter getChapter() {
		return chapter;
	}

	public String getVideo_name() {
		return video_name;
	}

	public String getVideo_src() {
		return video_src;
	}

	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public void setVideo_src(String video_src) {
		this.video_src = video_src;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Long getChapter_id() {
		return chapter_id;
	}

	public void setChapter_id(Long chapter_id) {
		this.chapter_id = chapter_id;
	}


}
