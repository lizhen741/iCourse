package com.iCourse.course.bean.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.iCourse.course.bean.Course;

public class CourseQueryDTO {
	private String course_name;
	private String course_desc;
	private String course_findbytype; // 按类型查找字段 ，多级类型查找优化
	private Boolean course_open;
	private Long teacher_id;
	private Long category_id;
	private String teacher_name;

	public Long getCategory_id() {
		return category_id;
	}

	public Boolean getCourse_open() {
		return course_open;
	}

	public void setCourse_open(Boolean course_open) {
		this.course_open = course_open;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_desc() {
		return course_desc;
	}

	public void setCourse_desc(String course_desc) {
		this.course_desc = course_desc;
	}

	public String getCourse_findbytype() {
		return course_findbytype;
	}

	public void setCourse_findbytype(String course_findbytype) {
		this.course_findbytype = course_findbytype;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public static Specification<Course> getWhere(CourseQueryDTO queryDTO) {
		return new Specification<Course>() {
			public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				// user orderList
				// root.join("orderList", JoinType.LEFT);

				/**
				 * 课程类型
				 */
				
				if (!StringUtils.isEmpty(queryDTO.getCategory_id())) {
					Join<Object, Object> join = root.join("category", JoinType.INNER);
					Predicate predicate1 = cb.equal(join.get("id").as(Long.class), queryDTO.getCategory_id());
					predicates.add(predicate1);
				}
				/**
				 * 课程名
				 */
				if (!StringUtils.isEmpty(queryDTO.getCourse_name())) {
					Predicate predicate2 = cb.like(root.get("course_name").as(String.class),
							"%" + queryDTO.getCourse_name() + "%");
					predicates.add(predicate2);
				}
				/**
				 * 课程授课老师id
				 */
				if (!StringUtils.isEmpty(queryDTO.getTeacher_id())) {
					// 创建关联对象(需要连接的另外一张表对象)
					// JoinType.INNER内连接(默认)
					// JoinType.LEFT左外连接
					// JoinType.RIGHT右外连接
					Join<Object, Object> join = root.join("teacher", JoinType.INNER);

					// join.get("name")连接表字段值
					Expression<Long> as = join.get("id").as(Long.class);
					Predicate predicate = cb.equal(as, queryDTO.getTeacher_id());
					predicates.add(predicate);

				}
				/**
				 * 课程授课老师名字
				 */
				if (!StringUtils.isEmpty(queryDTO.getTeacher_name())) {
					Join<Object, Object> join = root.join("teacher", JoinType.INNER);

					Predicate predicate = cb.like(join.get("teacher_name").as(String.class),
							"%" + queryDTO.getTeacher_name() + "%");
					predicates.add(predicate);

				}
				/**
				 * 课程是否公开
				 */
				if (!StringUtils.isEmpty(queryDTO.getCourse_open())) {
					Predicate predicate = cb.equal(root.get("course_open").as(Boolean.class),
							queryDTO.getCourse_open());
					predicates.add(predicate);

				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}

}
