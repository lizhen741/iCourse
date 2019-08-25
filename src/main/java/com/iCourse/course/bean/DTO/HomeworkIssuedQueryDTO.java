package com.iCourse.course.bean.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.iCourse.course.bean.HomeworkIssued;

public class HomeworkIssuedQueryDTO {

	private Long course_id;
	private String status;
	private Long clazz_id;

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getClazz_id() {
		return clazz_id;
	}

	public void setClazz_id(Long clazz_id) {
		this.clazz_id = clazz_id;
	}

	public static Specification<HomeworkIssued> getWhere(HomeworkIssuedQueryDTO queryDTO) {
		return new Specification<HomeworkIssued>() {
			public Predicate toPredicate(Root<HomeworkIssued> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicatesAnd = new ArrayList<Predicate>();
				List<Predicate> predicatesOr = new ArrayList<Predicate>();
				// user orderList
				// root.join("orderList", JoinType.LEFT);

				/**
				 * 根据课程选择
				 */
				if (!StringUtils.isEmpty(queryDTO.getCourse_id())) {
					Join<Object, Object> join = root.join("homework", JoinType.INNER);
					Predicate predicate = cb.equal(join.get("course_id").as(Long.class), queryDTO.getCourse_id());
					predicatesAnd.add(predicate);
				}
				/**
				 * 作业状态
				 */
				if (!StringUtils.isEmpty(queryDTO.getStatus())) {
					Predicate predicate = cb.equal(root.get("status").as(String.class), queryDTO.getStatus());
					predicatesAnd.add(predicate);
				}
				/**
				 * 题目类型
				 */
				if (!StringUtils.isEmpty(queryDTO.getClazz_id())) {
					Join<Object, Object> join = root.join("homeworkIssuedLinkClazzes", JoinType.INNER);
					Predicate predicate = cb.equal(join.get("clazzId").as(String.class), queryDTO.getClazz_id());
					predicatesAnd.add(predicate);
				}

				Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));

				return and;

			}
		};

	}

}
