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

import com.iCourse.course.bean.Homework;

public class HomeworkQueryDTO {
	private String homework_name;
	private Long course_id;
	
	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public String getHomework_name() {
		return homework_name;
	}

	public void setHomework_name(String homework_name) {
		this.homework_name = homework_name;
	}

	public static Specification<Homework> getWhere(HomeworkQueryDTO queryDTO) {
		return new Specification<Homework>() {
			public Predicate toPredicate(Root<Homework> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicatesAnd = new ArrayList<Predicate>();
				List<Predicate> predicatesOr = new ArrayList<Predicate>();
				// user orderList
				// root.join("orderList", JoinType.LEFT);

				
				/**
				 * 根据课程选择
				 */
				if (!StringUtils.isEmpty(queryDTO.getCourse_id())) {
					Predicate predicate = cb.equal(root.get("course_id").as(Long.class), queryDTO.getCourse_id());
					predicatesAnd.add(predicate);
				}
				/**
				 * 作业名称
				 */
				if (!StringUtils.isEmpty(queryDTO.getHomework_name())) {
					Predicate predicate = cb.like(root.get("status").as(String.class),
							"%" + queryDTO.getHomework_name() + "%");
					predicatesAnd.add(predicate);
				}

				Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));

				return and;

			}
		};

	}
}
