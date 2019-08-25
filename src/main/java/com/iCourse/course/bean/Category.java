package com.iCourse.course.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iCourse.common.bean.BaseEntity;

@Entity
@Table(name = "t_catagory")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Category extends BaseEntity<Long> implements Serializable {
	private String category_name;
	private String category_key;
	private List<Course> courses;
//	private String category_find;

	public String getCategory_name() {
		return category_name;
	}
	
	@OneToMany(mappedBy = "category",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JsonIgnore
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_key() {
		return category_key;
	}

	public void setCategory_key(String category_key) {
		this.category_key = category_key;
	}
//	public String getCategory_find() {
//		return category_find;
//	}
//	public void setCategory_find(String category_find) {
//		this.category_find = category_find;
//	}

}
