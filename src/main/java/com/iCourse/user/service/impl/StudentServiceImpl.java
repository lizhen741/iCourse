package com.iCourse.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Clazz;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.DTO.StudentQueryDTO;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ClazzDao clazzDao;

	@Override
	public void save(Student student) {
		studentDao.save(student);

	}

	@Override
	public Student findById(long id) {
		return studentDao.findById(id).get();
	}

	@Override
	public void joinClazz(Student student, Long clazz_id) {
		Clazz clazz2 = clazzDao.findById(clazz_id).get();
		clazz2.getStudents().add(student);
	}

	@Override
	public Page<Student> findStudentByClazz(Long clazz_id, Pageable pageable) {
		return studentDao.findStudentByClazz(clazz_id, pageable);
	}

	@Override
	public Page<Student> findAllByJPA(Specification<Student> spec, Pageable pageable) {
		return studentDao.findAll(spec, pageable);
	}

	@Override
	public Map<String, Object> getTeacherStudentLib(StudentQueryDTO query, Pageable pageable) {
		List<Long> clzzStudentIds = clazzDao.getClazzAllStudentId(query.getClazz_id());
		List<Long> ids = studentDao.findTeacherStudentLibStudentId(query.getTeacher_id());

		ids.removeAll(clzzStudentIds);

		Integer begin = pageable.getPageNumber() * pageable.getPageSize() >= ids.size()
				? ids.size()
				:pageable.getPageNumber() * pageable.getPageSize() ;
		Integer end = begin + pageable.getPageSize() >= ids.size() ?ids.size() 
				: begin + pageable.getPageSize();
		List<Long> findIds = ids.subList(begin, end);

		List<Student> students = (List<Student>) studentDao.findAllById(findIds);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data", students);
		map.put("total", ids.size());
		map.put("totalpage",ids.size()/pageable.getPageSize());
		Integer size = ids.size();
		map.put("totalsize", Long.parseLong(size.toString()));
		return map;
	}

}
