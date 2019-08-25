package com.iCourse.course.bean.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.Exercises;

public class ExercisesQueryDTO {
	private Long course_id;
	private List<Long> chapter_ids;
	private String exercises_type;
	private String difficulty;
	private String exercises_content;

	public Long getCourse_id() {
		return course_id;
	}

	public String getExercises_type() {
		return exercises_type;
	}

	public void setExercises_type(String exercises_type) {
		this.exercises_type = exercises_type;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getExercises_content() {
		return exercises_content;
	}

	public void setExercises_content(String exercises_content) {
		this.exercises_content = exercises_content;
	}

	public List<Long> getChapter_ids() {
		return chapter_ids;
	}

	public void setChapter_ids(List<Long> chapter_ids) {
		this.chapter_ids = chapter_ids;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public static Specification<Exercises> getWhere(ExercisesQueryDTO queryDTO) {
		return new Specification<Exercises>() {
			public Predicate toPredicate(Root<Exercises> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);
//				List eList=new ArrayList<>();
//				eList.add(root.<String>get("exercises_type"));
//				query.groupBy(eList);
				List<Predicate> predicatesAnd = new ArrayList<Predicate>();
				List<Predicate> predicatesOr = new ArrayList<Predicate>();
				// user orderList
				// root.join("orderList", JoinType.LEFT);

				/**
				 *  根据课程选择
				 */
				if (!StringUtils.isEmpty(queryDTO.getCourse_id())) {
					Predicate predicate = cb.equal(root.get("course_id").as(Long.class), queryDTO.getCourse_id());
					predicatesAnd.add(predicate);
				}
				/**
				 * 题目难度
				 */
				if (!StringUtils.isEmpty(queryDTO.getDifficulty())) {
					Predicate predicate = cb.equal(root.get("difficulty").as(String.class), queryDTO.getDifficulty());
					predicatesAnd.add(predicate);
				}
				/**
				 * 题目类型
				 */
				if (!StringUtils.isEmpty(queryDTO.getExercises_type())) {
					Predicate predicate = cb.equal(root.get("exercises_type").as(String.class), queryDTO.getExercises_type());
					predicatesAnd.add(predicate);
				}
				/**
				 * 题目名称
				 */
				if (!StringUtils.isEmpty(queryDTO.getExercises_content())) {
					Predicate predicate = cb.like(root.get("exercises_content").as(String.class), 
							"%" + queryDTO.getExercises_content() +"%");
					predicatesAnd.add(predicate);
				}

				/**
				 * 知识点
				 */
				if (queryDTO.getChapter_ids() != null) {
					for (Long id : queryDTO.getChapter_ids()) {
						Join<Object, Object> join = root.join("chapters", JoinType.INNER);
						Predicate predicate = cb.equal(join.get("id").as(Long.class), id);
						predicatesOr.add(predicate);
					}
				}

				Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
				
				if (predicatesOr.size() > 0) {
					Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
					return cb.and(or,and);
				}
				else {
					return and;
				}
					
				
					
				
			}
		};

	}
}
