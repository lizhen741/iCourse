package com.iCourse.user.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.Teacher;

public interface StudentDao extends PagingAndSortingRepository<Student , Long> ,JpaSpecificationExecutor<Student>{

	@Query(value="SELECT * from t_student where id in ( SELECT student_id from t_clazz_student where clazz_id = ?1)",nativeQuery=true)
	Page<Student> findStudentByClazz(Long clazz_id,Pageable pageable);

	@Query("select t.student.id from TeacherStudentLib t where t.teacher_id = ?1")
	List<Long> findTeacherStudentLibStudentId(Long teacher_id);
	
}
