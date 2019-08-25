package com.iCourse.course.bean.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.iCourse.course.bean.Chapter;

public class ChapterQueryDTO {
	private Integer chapter_level;
	private Boolean leaf;
	private Long chapter_parent;
	private Long course_id;
	
	
	public Integer getChapter_level() {
		return chapter_level;
	}
	public void setChapter_level(Integer chapter_level) {
		this.chapter_level = chapter_level;
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
	
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public static Specification<Chapter> getWhere(ChapterQueryDTO queryDTO) {
		return new Specification<Chapter>() {
			public Predicate toPredicate(Root<Chapter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				// user orderList
				// root.join("orderList", JoinType.LEFT);

				if (!StringUtils.isEmpty(queryDTO.getCourse_id()) && queryDTO.getCourse_id() != null) {
					Predicate predicate1 = cb.equal(root.get("course_id").as(Long.class), queryDTO.getCourse_id());
					predicates.add(predicate1);
				}
				if (!StringUtils.isEmpty(queryDTO.getChapter_parent()) && queryDTO.getChapter_parent() != null) {
					Predicate predicate1 = cb.equal(root.get("chapter_parent").as(Long.class), queryDTO.getChapter_parent());
					predicates.add(predicate1);
				}
				if (!StringUtils.isEmpty(queryDTO.getChapter_level()) && queryDTO.getChapter_level() != null) {
					Predicate predicate1 = cb.equal(root.get("chapter_level").as(Integer.class), queryDTO.getChapter_level());
					predicates.add(predicate1);
				}
				if (!StringUtils.isEmpty(queryDTO.getLeaf()) && queryDTO.getLeaf() != null) {
					Predicate predicate1 = cb.equal(root.get("leaf").as(Boolean.class), queryDTO.getLeaf());
					predicates.add(predicate1);
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}
}
