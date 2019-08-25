package com.iCourse.user.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iCourse.user.bean.StuQualificate;
import com.iCourse.user.bean.Student;
import com.iCourse.user.dao.StuQualificateDao;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.service.StuQualificateService;

@Service
@Transactional
public class StuQualificateServiceImpl implements StuQualificateService {
	@Autowired
	private StuQualificateDao stuQualificateDao;
	@Autowired
	private StudentDao studentDao;

	@Override
	public void createQualificate(StuQualificate stuQualificate) {
		Student student = studentDao.findById(stuQualificate.getStudent().getId()).get();
		student.setQualificate(stuQualificate);
		stuQualificateDao.save(stuQualificate);
	}

	@Override
	public Page<StuQualificate> findStudentQualificate(Specification<StuQualificate> spec, Pageable pageable) {
		return stuQualificateDao.findAll(spec, pageable);
	}

	@Override
	public Integer passStudentQualificate(Long sq_id, Long id) {
		StuQualificate stuQualificate = stuQualificateDao.findById(sq_id).get();

		if (stuQualificate == null)
			return -1;
		if (stuQualificate.getTeacher().getId() == id) {
			stuQualificate.setPass(true);
			stuQualificate.setIsCheck(true);
			stuQualificate.setPass_time(new Date());
			Student student = stuQualificate.getStudent();
			student.setCertificate(stuQualificate.getStuCertificate());
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public Integer failStudentQualificate(Long sq_id, Long id) {
		StuQualificate stuQualificate = stuQualificateDao.findById(sq_id).get();

		if (stuQualificate == null)
			return -1;
		if (stuQualificate.getTeacher().getId() == id) {
			stuQualificate.setPass(false);
			stuQualificate.setIsCheck(true);
			stuQualificate.setFail_time(new Date());
			return 1;
		} else {
			return 0;
		}
	}

}
