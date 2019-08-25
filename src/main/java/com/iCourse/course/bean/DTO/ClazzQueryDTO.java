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

import com.iCourse.course.bean.Clazz;

public class ClazzQueryDTO {
	private Boolean clazz_status;
	private Long course_id;
	private Long student_id;
	public Boolean getClazz_status() {
		return clazz_status;
	}
	public void setClazz_status(Boolean clazz_status) {
		this.clazz_status = clazz_status;
	}
	public Long getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}
	
	public Long getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}
	public static Specification<Clazz> getWhere(ClazzQueryDTO queryDTO) {
		return new Specification<Clazz>() {
			public Predicate toPredicate(Root<Clazz> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				// user orderList
				// root.join("orderList", JoinType.LEFT);

			
				/**
				 * 课堂状态
				 */
				if (!StringUtils.isEmpty(queryDTO.getClazz_status())) {
					Predicate predicate2 = cb.equal(root.get("clazz_status").as(Boolean.class),
							queryDTO.getClazz_status());
					predicates.add(predicate2);
				}
				/**
				 * 课程id
				 */
				if (!StringUtils.isEmpty(queryDTO.getCourse_id())) {
					Join<Object, Object> join = root.join("course", JoinType.INNER);
				
					Predicate predicate1 = cb.equal(join.get("id").as(Long.class), queryDTO.getCourse_id());
					predicates.add(predicate1);
				}
				/**
				 * 学生id
				 */
				if (!StringUtils.isEmpty(queryDTO.getStudent_id())) {
					Join<Object, Object> join = root.join("students", JoinType.INNER);
					Predicate predicate1 = cb.equal(join.get("id").as(Long.class), queryDTO.getStudent_id());
					predicates.add(predicate1);
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}
}
