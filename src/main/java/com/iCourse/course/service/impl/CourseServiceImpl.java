package com.iCourse.course.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.DTO.CourseQueryDTO;
import com.iCourse.course.dao.CourseDao;
import com.iCourse.course.service.CourseService;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.dao.TeacherDao;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private TeacherDao teacherDao;
	
	public void save(Teacher teacher,Course course) {
		course.setTeacher(teacher);
		courseDao.save(course);
	}

	@Override
	public Course findById(long id) {
		return courseDao.findById(id).get();
	}

	@Override
	public void delete(Long id) {
//		courseDao.deleteById(id);
		courseDao.delete(courseDao.findById(id).get());
	}

	@Override
	public List<Course> findAll(CourseQueryDTO query) {
		return courseDao.findAll(query.getWhere(query));
		
	}

	@Override
	public Page<Course> findAll(CourseQueryDTO query, Pageable pageable) {
		return courseDao.findAll(query.getWhere(query), pageable);
		
	}

	@Override
	public void deleteById(long id) {
		courseDao.deleteById(id);
		
	}

	@Override
	public Page<Course> findStudentCourseList(Long student_id,Pageable pageable) {
		return courseDao.findStudentCourseList(student_id,pageable);
	}

	@Override
	public List<Course> getCoursesForSettingPower(Long teacher_id) {
		return courseDao.findCourseByTeacherId(teacher_id);
	}

	@Override
	public List<Long> getOpenCourseIds(Long teacher_id) {
		return courseDao.getOpenCourseIds(teacher_id);
	}

	@Override
	public void setCourseOpen(List<Long> course_ids) {
		List<Course> courses = (List<Course>) courseDao.findAllById(course_ids);
		for (Course course : courses) {
			course.setCourse_open(true);
		}
	}

	@Override
	public void closeCourseOpen(List<Long> course_ids) {
		List<Course> courses = (List<Course>) courseDao.findAllById(course_ids);
		for (Course course : courses) {
			course.setCourse_open(false);
		}
	}

	@Override
	public Page<Course> findAll(Specification<Course> spec, Pageable pageable) {
		return courseDao.findAll(spec, pageable);
	}

	@Override
	public List<Course> findAllById(List<Long> course_ids) {
		return (List<Course>) courseDao.findAllById(course_ids);
	}
	
	
//	public void update(Course course) {
//		courseDao.update(course);
//	}
}
