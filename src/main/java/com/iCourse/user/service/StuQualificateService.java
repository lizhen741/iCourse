package com.iCourse.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iCourse.user.bean.StuQualificate;

public interface StuQualificateService {

	void createQualificate(StuQualificate stuQualificate);

	Page<StuQualificate> findStudentQualificate(Specification<StuQualificate> spec, Pageable pageable);

	Integer passStudentQualificate(Long sq_id, Long id);

	Integer failStudentQualificate(Long sq_id, Long id);

	

}
