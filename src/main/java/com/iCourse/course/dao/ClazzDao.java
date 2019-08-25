package com.iCourse.course.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.iCourse.course.bean.Clazz;

public interface ClazzDao extends PagingAndSortingRepository<Clazz , Long>,JpaSpecificationExecutor<Clazz> {

//	@Query("from Clazz c where c.course_id = ?1 and c.clazz_status = ?2")
//	Page<Clazz> findByCourseId(Long course_id,Boolean status,Pageable pageable);
	
	@Modifying 
	@Query("update Clazz c set " +
            "c.clazz_name = CASE WHEN (LENGTH(trim(:#{#clazz.clazz_name})) < 1 ) THEN c.clazz_name ELSE :#{#clazz.clazz_name} END ," +
            "c.clazz_begin = CASE WHEN :#{#clazz.clazz_begin} IS NULL THEN c.clazz_begin ELSE :#{#clazz.clazz_begin} END ," +
            "c.clazz_end = CASE WHEN :#{#clazz.clazz_end} IS NULL THEN c.clazz_end ELSE :#{#clazz.clazz_end} END ," +
            "c.clazz_syn =  CASE WHEN :#{#clazz.clazz_syn} IS NULL THEN c.clazz_syn ELSE :#{#clazz.clazz_syn} END " +
            "where c.id = :#{#clazz.id}")
	void update(@Param("clazz") Clazz clazz);
	
	
	@Query(value="select * from t_clazz where id in (select clazz_id from t_clazz_student where student_id = ?1)",nativeQuery=true)
	Page<Clazz> findClazzByStudent(Long student_id,Pageable pageable);

	@Modifying
	@Query(value="delete from t_clazz_student where clazz_id = ?1 and student_id in ( ?2 )",nativeQuery=true)
	int clazzDelStudent(Long clazz_id, String stuIds);

	
	@Query("from Clazz c where c.clazz_serial_number = ?1")
	Clazz findByServial(String clazz_serial_number);


	@Query("select new Clazz(c.id,c.clazz_name) from Clazz c where c.course.id = ?1")
	List<Clazz> getClazzesForHomework(Long clazz_id);


	@Query("select distinct c from Clazz c inner join fetch Student s on s.id=?1 where s.id = ?1")
	Page<Clazz> getClazzesByStudent(Long student_id,Pageable pageable);

	@Query(value = "SELECT student_id from t_clazz_student where clazz_id = ?1",nativeQuery=true)
	List<Long> getClazzAllStudentId(Long clazz_id);
	
}
