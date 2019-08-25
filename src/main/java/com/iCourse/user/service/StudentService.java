package com.iCourse.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.DTO.StudentQueryDTO;

public interface StudentService {

	void save(Student student);

	Student findById(long id);

	void joinClazz(Student student, Long clazz_id);

	Page<Student> findStudentByClazz(Long id, Pageable pageable);

	Page<Student> findAllByJPA(Specification<Student> spec, Pageable pageable);

	Map<String, Object> getTeacherStudentLib(StudentQueryDTO query,Pageable pageable);

}
