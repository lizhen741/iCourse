package com.iCourse.user.bean.DTO;

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

import com.iCourse.user.bean.Student;

public class StudentQueryDTO {
	private Long teacher_id;
	private Long clazz_id;

	public Long getClazz_id() {
		return clazz_id;
	}

	public void setClazz_id(Long clazz_id) {
		this.clazz_id = clazz_id;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public static Specification<Student> getWhere(StudentQueryDTO queryDTO) {
		return new Specification<Student>() {
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				/**
				 * 学生id
				 */
				if (!StringUtils.isEmpty(queryDTO.getTeacher_id())) {
					Join<Object, Object> join = root.join("teacherStudentLib", JoinType.INNER);
					Predicate predicate1 = cb.equal(join.get("teacher_id").as(Long.class), queryDTO.getTeacher_id());
					predicates.add(predicate1);
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}

}
