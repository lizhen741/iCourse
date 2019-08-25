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

import com.iCourse.course.bean.StudentHomework;

public class StudentHomeworkQueryDTO {

	private Long homeworkIssued_id;
	private Long clazz_id;
	private String status;
	private String student_name;

	public String getStatus() {
		return status;
	}



	public String getStudent_name() {
		return student_name;
	}



	public void setStudent_name(String student_name) {
		this.student_name = student_name;
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



	public Long getHomeworkIssued_id() {
		return homeworkIssued_id;
	}



	public void setHomeworkIssued_id(Long homeworkIssued_id) {
		this.homeworkIssued_id = homeworkIssued_id;
	}



	public static Specification<StudentHomework> getWhere(StudentHomeworkQueryDTO queryDTO) {
		return new Specification<StudentHomework>() {
			public Predicate toPredicate(Root<StudentHomework> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicatesAnd = new ArrayList<Predicate>();
				List<Predicate> predicatesOr = new ArrayList<Predicate>();
				// user orderList
				// root.join("orderList", JoinType.LEFT);

				
				/**
				 * 根据某次正式发布的作业id找相关学生
				 */
				if (!StringUtils.isEmpty(queryDTO.getHomeworkIssued_id())) {
					Join<Object, Object> join = root.join("homeworkissued_link_clazz", JoinType.INNER);
					Predicate predicate = cb.equal(join.get("homeworkIssuedId").as(Long.class), queryDTO.getHomeworkIssued_id());
					predicatesAnd.add(predicate);
				}
				
				/**
				 * 根据班级筛选
				 */
				if (!StringUtils.isEmpty(queryDTO.getClazz_id())) {
					Join<Object, Object> join = root.join("homeworkissued_link_clazz", JoinType.INNER);
					Predicate predicate = cb.equal(join.get("clazzId").as(Long.class), queryDTO.getClazz_id());
					predicatesAnd.add(predicate);
				}
				/**
				 * 根据状态筛选
				 */
				if(!StringUtils.isEmpty(queryDTO.getStatus())){
					Predicate predicate = cb.equal(root.get("status").as(String.class), queryDTO.getStatus());
					predicatesAnd.add(predicate);
				}
				/**
				 * 根据学生名称筛选
				 */
				if(!StringUtils.isEmpty(queryDTO.getStudent_name())){
					Join<Object, Object> join = root.join("student", JoinType.INNER);
					Predicate predicate = cb.like(join.get("student_name").as(String.class),
							"%" + queryDTO.getStudent_name() + "%");
					predicatesAnd.add(predicate);
				}

				Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));

				return and;

			}
		};

	}
}
