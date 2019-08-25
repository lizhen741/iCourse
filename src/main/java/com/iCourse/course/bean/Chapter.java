package com.iCourse.course.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name = "t_chapter")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Chapter extends BaseEntity<Long> implements Serializable {
	private String chapter_name;
	private Long chapter_parent;
	private Integer chapter_level;
	private Boolean leaf;
	private Boolean disable;
	private Long course_id;
	private Video video;
	private List<Chapter> child_chapters = new ArrayList<Chapter>();

	private Chapter chapter;
	private Course course;

	@ManyToOne
	@JoinColumn(name = "course")
	@JsonBackReference
	public Course getCourse() {
		return course;
	}

	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JsonManagedReference
	public Video getVideo() {
		return video;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "chapter_pid")
	@JsonBackReference
	public List<Chapter> getChild_chapters() {
		return child_chapters;
	}

	@ManyToOne
	@JoinColumn(name = "chapter_pid")
	public Chapter getChapter() {
		return chapter;
	}

	
	
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public void setChild_chapters(List<Chapter> child_chapters) {
		this.child_chapters = child_chapters;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getChapter_name() {
		return chapter_name;
	}

	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Long getChapter_parent() {
		return chapter_parent;
	}

	public void setChapter_parent(Long chapter_parent) {
		this.chapter_parent = chapter_parent;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Integer getChapter_level() {
		return chapter_level;
	}

	public void setChapter_level(Integer chapter_level) {
		this.chapter_level = chapter_level;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Override
	public String toString() {
		return "Chapter [chapter_name=" + chapter_name + ", chapter=" + chapter + "]";
	}

	

}
