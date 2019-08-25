package com.iCourse.course.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.Course;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.CourseDao;
import com.iCourse.course.service.ClazzService;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.TeacherStudentLib;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.dao.TeacherStudentLibDao;

@Service
@Transactional
public class ClazzServiceImpl implements ClazzService{

	@Autowired
	private ClazzDao clazzDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private TeacherStudentLibDao teacherStudentLibDao;
	
	
	@Override
	public void save(Clazz clazz,Long course_id) {
		Course course = courseDao.findById(course_id).get();
		clazz.setCourse(course);
		clazzDao.save(clazz);
		
	}

//	public Page<Clazz> findByCourseId(Clazz clazz, Pageable pageable) {
//		return clazzDao.findByCourseId(clazz.getCourse_id(),clazz.getClazz_status(), pageable);
//	}
	
	public Page<Clazz> findByCourse(Specification<Clazz> spec, Pageable pageable) {
		return clazzDao.findAll(spec, pageable);
	}
	
	@Override
	public void update(Clazz clazz) {
		clazzDao.update(clazz);
		
	}
	@Override
	public int clazzAddStudents(Clazz clazz,List<Long> ids) {
		Iterable<Student> students = studentDao.findAllById(ids);
		Clazz clazz2 = clazzDao.findById(clazz.getId()).get();
		int i = 0;
		for (Student student : students) {
			clazz2.getStudents().add(student);
			i++;
		}
		
		return i;
	}
	@Override
	public int clazzDelStudents(Long clazz_id,Long[]  ids) {
		Clazz clazz = clazzDao.findById(clazz_id).get();
		List<Student> students = clazz.getStudents();
		List<Student> del = new ArrayList<Student>();
		for (Long id : ids) {
			for (Student student : students) {
				if(id==student.getId()) {
					del.add(student);
				}
			}
		}
		students.removeAll(del);
		return del.size();
	}
	@Override
	public Clazz findBySerail(String clazz_serial_number) {
		
		return clazzDao.findByServial(clazz_serial_number);
	}

	@Override
	public void joinClazz(Long clazz_id,Student student) {
		Clazz clazz = clazzDao.findById(clazz_id).get();
		clazz.getStudents().add(student);
		TeacherStudentLib teacherStudentLib = new TeacherStudentLib();
		teacherStudentLib.setStudent(student);
		teacherStudentLib.setTeacher_id(clazz.getCourse().getTeacher().getId());
		teacherStudentLibDao.save(teacherStudentLib);
		
	}

	@Override
	public List<Clazz> getClazzesForHomework(Long clazz_id) {
		return clazzDao.getClazzesForHomework(clazz_id);
	}

	@Override
	public Page<Clazz> getClazzesByStudent(Long student_id , Pageable pageable) {
		return clazzDao.getClazzesByStudent(student_id,pageable);
	}

	@Override
	public Page<Clazz> getClazzesByStudentSpec(Specification<Clazz> spec, Pageable pageable) {
		return clazzDao.findAll(spec, pageable);
	}

	@Override
	public List<Long> getClazzAllStudentId(Long clazz_id) {
		return clazzDao.getClazzAllStudentId(clazz_id);
	}

	
	
	

}
