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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iCourse.common.bean.BaseEntity;
import com.iCourse.user.bean.Teacher;

@Entity
@Table(name = "t_course")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Course extends BaseEntity<Long> implements Serializable {
	private String course_name;
	private String course_desc;
	private String course_cover;
	private Boolean course_open;
//	private String course_findbytype; // 按类型查找字段 ，多级类型查找优化
	/* 这，然而并没什么卵用，留着做级联删除算了 */
	private List<Chapter> chapters = new ArrayList<Chapter>();
	private List<Clazz> clazzes = new ArrayList<>();
	private Teacher teacher;
	private Category category;

	@ManyToOne
	@JoinColumn(name = "caretory_id")
	public Category getCategory() {
		return category;
	}

	@OneToMany(mappedBy = "course", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JsonIgnore
	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}

	@ManyToOne
	@JoinColumn(name="teacher_id")
	@JsonManagedReference
	public Teacher getTeacher() {
		return teacher;
	}

	@OneToMany(mappedBy="course",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference // json化时忽略
	public List<Chapter> getChapters() {
		return chapters;
	}

	public String getCourse_name() {
		return course_name;
	}

	public Boolean getCourse_open() {
		return course_open;
	}

	public void setCourse_open(Boolean course_open) {
		this.course_open = course_open;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public String getCourse_cover() {
		return course_cover;
	}

	public void setCourse_cover(String course_cover) {
		this.course_cover = course_cover;
	}

	public String getCourse_desc() {
		return course_desc;
	}

	public void setCourse_desc(String course_desc) {
		this.course_desc = course_desc;
	}


	public Course(Long id,String course_name) {
		super(id);
		this.course_name = course_name;
	}

	public Course() {
		super();
	}

	@Override
	public String toString() {
		return "Course [course_name=" + course_name + ", course_desc=" + course_desc + ", course_cover=" + course_cover
				+ ", category=" + category + "]";
	}



}
