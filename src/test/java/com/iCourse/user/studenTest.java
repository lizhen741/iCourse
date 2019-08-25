package com.iCourse.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.iCourse.BaseTest;
import com.iCourse.common.bean.PageInfo;
import com.iCourse.user.bean.StuCertificate;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.DTO.StudentQueryDTO;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.service.StudentService;

public class studenTest extends BaseTest{

	@Autowired
	private StudentDao sst;
	@Autowired
	private StudentService ss;
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void addStudent() {
		
		Student s = new Student();
		s.setStudent_name("张三");
		s.setStudent_phone("123456");
		StuCertificate st = new StuCertificate();
		st.setEducation("本科");
		st.setName("张三");
		st.setSchool("dgut");
		st.setClazzname("软件1班");
		s.setCertificate(st);
		
		sst.save(s);
	}
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void setCertificate() {
		Student student = sst.findById(1l).get();
		StuCertificate st = new StuCertificate();
		st.setEducation("本科");
		st.setName("李四");
		st.setSchool("dgut");
		st.setClazzname("软件1班");
		student.setCertificate(st);
	}
	
	@Test
	@Rollback(value=false)
	@Transactional
	public void findStudentByIds() {
		List<Long> ids = new ArrayList<Long>();
		for (Long i = 1l; i < 5; i++) {
			ids.add(i);
		}
		
		Iterable<Student> findAllById = sst.findAllById(ids);
		
		for (Student student : findAllById) {
			System.out.println(student);
		}
	}
	
	@Test
	@Rollback(value=false)
	public void demo() {
		PageInfo<Student> pageInfo = new PageInfo<Student>();
		pageInfo.initPage(1, 8);
		StudentQueryDTO query = new StudentQueryDTO();
		query.setClazz_id(4l);
		query.setTeacher_id(1l);
		Map<String, Object> map = ss.getTeacherStudentLib(query, pageInfo.getPageable());
		System.out.println(map);
	}
}
