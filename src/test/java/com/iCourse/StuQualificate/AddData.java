package com.iCourse.StuQualificate;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.iCourse.BaseTest;
import com.iCourse.user.bean.StuCertificate;
import com.iCourse.user.bean.StuQualificate;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.dao.StuQualificateDao;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.dao.TeacherDao;

public class AddData extends BaseTest{
	@Autowired
	private StuQualificateDao sqd;
	@Autowired
	private StudentDao sd;
	@Autowired
	private TeacherDao td;
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void create() {
		StuQualificate stuQualificate = new StuQualificate();
		StuCertificate stuCertificate = new StuCertificate();
		Student student = new Student();
		student.setId(23l);
		Teacher teacher = td.findById(1l).get();
		
		stuCertificate.setClazzname("软件工程2016级1班");
		stuCertificate.setEducation("本科");
		stuCertificate.setGrade("2016级");
		stuCertificate.setSchool("dgut测试认证");
		stuCertificate.setSpeciality("软工");
		stuCertificate.setName("测试认证学生");
		
		stuQualificate.setStuCertificate(stuCertificate);
		stuQualificate.setStudent(student);
		stuQualificate.setTeacher(teacher);
		stuQualificate.setCreate_time(new Date());
		
		student.setQualificate(stuQualificate);
		sqd.save(stuQualificate);
	}
	
}
