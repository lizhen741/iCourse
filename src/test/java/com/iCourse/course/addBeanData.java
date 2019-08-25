/**
 *   添加前端显示的数据
 */
package com.iCourse.course;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.iCourse.BaseTest;
import com.iCourse.course.bean.Category;
import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.Course;
import com.iCourse.course.dao.CategoryDao;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.CourseDao;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.StuCertificate;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.TeachCertificate;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.dao.AccountDao;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.dao.TeacherDao;

public class addBeanData extends BaseTest {

	@Autowired
	private CourseDao cdao;
	@Autowired
	private TeacherDao tdao;
	@Autowired
	private CategoryDao cadao;
	@Autowired
	private StudentDao stdao;
	@Autowired
	private ClazzDao cldao;
	@Autowired
	private AccountDao adao;

	@Test
	@Rollback(value = false)
	@Transactional
	public void teacher() {
		Teacher teacher = new Teacher();
		teacher.setTeacher_name("元帅");

		TeachCertificate teachCertificate = new TeachCertificate();
		teachCertificate.setSchool("东莞理工学院");
		teachCertificate.setName("元帅");
		teacher.setCertificate(teachCertificate);
		tdao.save(teacher);
		Account account = new Account();
		account.setAccount_num("teahcer2");
		account.setName("元帅");
		account.setPassword("123456");
		account.setTeacher(teacher);
		adao.save(account);
	}

	@Test
	@Rollback(value = false)
	@Transactional
	public void addCategory() {
		Category type = new Category();
		type.setCategory_name("计算机");
		type.setCategory_key("computer");
		Category type1 = new Category();
		type1.setCategory_name("艺术设计");
		type1.setCategory_key("art");
		cadao.save(type);
		cadao.save(type1);
	}
	
	@Test
	@Rollback(value = false)
	@Transactional
	public void addCourse() {
		Teacher teacher = tdao.findById(1l).get();
		Category category = cadao.findById(1l).get();
		Course course = new Course();
		course.setCourse_name("javaee");
		course.setCategory(category);
		course.setTeacher(teacher);
		cdao.save(course);
		
	}

	@Test
	@Rollback(value = false)
	@Transactional
	public void student() {
		for (int i = 20; i < 30; i++) {
			Student student = new Student();
			Account account = new Account();
			student.setStudent_name("test类学生" + i);
			account.setAccount_num("12318"+i);
			account.setName("test类学生" + i);
			account.setStudent(student);
			stdao.save(student);
			adao.save(account);
		}

	}
	@Test
	@Rollback(value = false)
	@Transactional
	public void stuCertificate() {
		Iterable<Student> stus = stdao.findAll();
		int i = 1;
		for (Student student : stus) {
			if(student.getCertificate()!=null)
				continue;
			StuCertificate stuCertificate = new StuCertificate();
			stuCertificate.setName(student.getStudent_name());
			stuCertificate.setSchool("东莞理工");
			stuCertificate.setEducation("本科");
			stuCertificate.setStudent_num("1265"+i*11);
			stuCertificate.setClazzname("计算机科学2016级1班");
			student.setCertificate(stuCertificate);
			i++;
		}

	}
	
	@Test
	@Rollback(value = false)
	@Transactional
	public void findCourseByTeacherId() {
		List<Course> courses = cdao.findCourseByTeacherId(1l);
		for (Course course : courses) {
			System.out.println(course);
		}
	}

	@Test
	@Rollback(value = false)
	@Transactional
	public void addStudentToClazz() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(10l);
		ids.add(12l);
		ids.add(13l);
		ids.add(14l);
		ids.add(15l);
		ids.add(16l);
		ids.add(17l);
		
		Iterable<Student> stus = stdao.findAllById(ids);
		Clazz clazz = cldao.findById(5l).get();
		for (Student student : stus) {
			clazz.getStudents().add(student);
		}
	}
	
//	@Test
//	@Rollback(value = false)
//	@Transactional
//	public void findStudentByClazz() {
//		Sort sort = Sort.by(Sort.Direction.DESC, "id");
//		PageRequest page = PageRequest.of(1, 1, sort);
//		
//		Page<Student> students = stdao.findStudentByClazz(1l, page);
//		for (Student student : students) {
//			System.out.println(student);
//		}
//	}
//
//	@Test
//	@Rollback(value = false)
//	@Transactional
//	public void findClazzByStudent() {
//		Sort sort = Sort.by(Sort.Direction.DESC, "id");
//
//		PageRequest page = PageRequest.of(0, 1, sort);
//
//		Page<Clazz> clazzes = cldao.findClazzByStudent(11l, page);
//		for (Clazz clazz : clazzes) {
//			System.out.println(clazz.getClazz_name());
//		}
//	}

}
