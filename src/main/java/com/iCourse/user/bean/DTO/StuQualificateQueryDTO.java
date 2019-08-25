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

import com.iCourse.user.bean.StuQualificate;

public class StuQualificateQueryDTO {
	private Long teacher_id;
	private Boolean isCheck;
	private Boolean pass;
	public Long getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}
	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
	public Boolean getPass() {
		return pass;
	}
	public void setPass(Boolean pass) {
		this.pass = pass;
	}
	
	public static Specification<StuQualificate> getWhere(StuQualificateQueryDTO queryDTO) {
		return new Specification<StuQualificate>() {
			public Predicate toPredicate(Root<StuQualificate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				// user orderList
				// root.join("orderList", JoinType.LEFT);

			
				/**
				 * 判断已审核
				 */
				if (!StringUtils.isEmpty(queryDTO.getIsCheck())) {
					Predicate predicate2 = cb.equal(root.get("isCheck").as(Boolean.class),
							queryDTO.getIsCheck());
					predicates.add(predicate2);
				}
				/**
				 * 判断已通过
				 */
				if (!StringUtils.isEmpty(queryDTO.getPass())) {
					Predicate predicate2 = cb.equal(root.get("pass").as(Boolean.class),
							queryDTO.getPass());
					predicates.add(predicate2);
				}
				/**
				 * 教师id筛选
				 */
				if (!StringUtils.isEmpty(queryDTO.getTeacher_id())) {
					Join<Object, Object> join = root.join("teacher", JoinType.INNER);
					Predicate predicate1 = cb.equal(join.get("id").as(Long.class), queryDTO.getTeacher_id());
					predicates.add(predicate1);
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}
	
}
