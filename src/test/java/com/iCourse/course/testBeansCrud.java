package com.iCourse.course;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.iCourse.BaseTest;
import com.iCourse.course.bean.Category;
import com.iCourse.course.bean.Chapter;
import com.iCourse.course.bean.Course;
import com.iCourse.course.bean.Video;
import com.iCourse.course.bean.DTO.ChapterQueryDTO;
import com.iCourse.course.dao.CategoryDao;
import com.iCourse.course.dao.ChapterDao;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.CourseDao;
import com.iCourse.course.dao.VideoDao;
import com.iCourse.course.service.ChapterService;
import com.iCourse.course.service.CourseService;
import com.iCourse.user.bean.TeachCertificate;
import com.iCourse.user.bean.Teacher;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.dao.TeacherDao;

public class testBeansCrud extends BaseTest {

	@Autowired
	private CourseService cs;
	@Autowired
	private CourseDao cdao;
	@Autowired
	private TeacherDao ts;
	@Autowired
	private CategoryDao cd;
	@Autowired
	private ChapterService chas;
	@Autowired
	private ChapterDao chd;
	@Autowired
	private VideoDao vdao;
	@Autowired
	private ClazzDao cldao;
	@Autowired
	private StudentDao stdao;


	/**
	 * 测试teacher和course 一对多关系 CRUD
	 *  结论：添加course时，先从数据库找出teacher，course设置teacher属性 再保存course
	 * 
	 */
	@Test
	@Rollback(value = false)
	@Transactional
	public void teacherAndCourse() {
//		Teacher teacher = new Teacher();
//		TeachCertificate tc = new TeachCertificate();
//		teacher.setTeacher_name("刘立平");
//		teacher.setTeacher_email("123456987@163.com");
//		tc.setName("刘立平");
//		tc.setSchool("东莞理工学院");
//		tc.setSpecialitie("计算机与网络安全");
//		teacher.setCertificate(tc);
//		ts.save(teacher);

		/**
		 * 使用course设置teacher属性，再保存course发1条SQL语句（不包含teacher的查找）
		 * Hibernate: select teacher0_.id as id1_8_0_, teacher0_.certificate_id as certific5_8_0_, teacher0_.teacher_email as teacher_2_8_0_, teacher0_.teacher_name as teacher_3_8_0_, teacher0_.teacher_picture as teacher_4_8_0_ from t_teacher teacher0_ where teacher0_.id=?
		 * Hibernate: insert into t_course (course_cover, course_desc, course_findbytype, course_name, course_type, teacher, teacher_id) values (?, ?, ?, ?, ?, ?, ?)
		 *
		 * 使用查找出来得teacher持久态对象，往teachet的courses列表中添加course，触发自动更新
		 * 会发送3条SQL语句	为什么呀？？？（不包含teacher的查找）。效率低！！！！
		 * Hibernate: select teacher0_.id as id1_8_0_, teacher0_.certificate_id as certific5_8_0_, teacher0_.teacher_email as teacher_2_8_0_, teacher0_.teacher_name as teacher_3_8_0_, teacher0_.teacher_picture as teacher_4_8_0_ from t_teacher teacher0_ where teacher0_.id=?
		 * Hibernate: select courses0_.teacher as teacher8_4_0_, courses0_.id as id1_4_0_, courses0_.id as id1_4_1_, courses0_.course_cover as course_c2_4_1_, courses0_.course_desc as course_d3_4_1_, courses0_.course_findbytype as course_f4_4_1_, courses0_.course_name as course_n5_4_1_, courses0_.course_type as course_t6_4_1_, courses0_.teacher as teacher8_4_1_, courses0_.teacher_id as teacher_7_4_1_ from t_course courses0_ where courses0_.teacher=?
		 * Hibernate: insert into t_course (course_cover, course_desc, course_findbytype, course_name, course_type, teacher, teacher_id) values (?, ?, ?, ?, ?, ?, ?)
		 * Hibernate: update t_course set teacher=? where id=?
		 */
		Teacher teacher = ts.findById(4l).get();
		Course course = new Course();
		course.setCourse_name("javaee3");
//		teacher.getCourses().add(course);
		course.setTeacher(teacher);
		cdao.save(course);
		/**
		 * 级联删除 ：先移出course中的外键在删除teacher和course数据
		 * Hibernate: update t_course set teacher=null where teacher=? 
		 * Hibernate: delete from t_course where id=? Hibernate: delete from t_teacher where id=?
		 * Hibernate: delete from t_teach_certificate where id=?
		 */
//		ts.delete(teacher);
		
		/**
		 * 删除course， 一条删除语句，第一条查找不算，第二条是因为course关联了chapter也不算
		 * 确认删除course @ManyToOne(cascade={CascadeType.PERSIST})注解不会级联删除teacher。
		 * course和chapter，一对多的关系删除会是怎样？
		 * Hibernate: select course0_.id as id1_4_0_, course0_.course_cover as course_c2_4_0_, course0_.course_desc as course_d3_4_0_, course0_.course_findbytype as course_f4_4_0_, course0_.course_name as course_n5_4_0_, course0_.course_type as course_t6_4_0_, course0_.teacher as teacher8_4_0_, course0_.teacher_id as teacher_7_4_0_, teacher1_.id as id1_8_1_, teacher1_.certificate_id as certific5_8_1_, teacher1_.teacher_email as teacher_2_8_1_, teacher1_.teacher_name as teacher_3_8_1_, teacher1_.teacher_picture as teacher_4_8_1_ from t_course course0_ left outer join t_teacher teacher1_ on course0_.teacher=teacher1_.id where course0_.id=?
		 * Hibernate: select chapters0_.course as course8_1_0_, chapters0_.id as id1_1_0_, chapters0_.id as id1_1_1_, chapters0_.chapter_pid as chapter_7_1_1_, chapters0_.chapter_level as chapter_2_1_1_, chapters0_.chapter_name as chapter_3_1_1_, chapters0_.chapter_parent as chapter_4_1_1_, chapters0_.course as course8_1_1_, chapters0_.course_id as course_i5_1_1_, chapters0_.leaf as leaf6_1_1_, chapters0_.video_id as video_id9_1_1_, video1_.id as id1_9_2_, video1_.chapter_key as chapter_6_9_2_, video1_.chapter_id as chapter_2_9_2_, video1_.course_id as course_i3_9_2_, video1_.video_name as video_na4_9_2_, video1_.video_src as video_sr5_9_2_, chapter2_.id as id1_1_3_, chapter2_.chapter_pid as chapter_7_1_3_, chapter2_.chapter_level as chapter_2_1_3_, chapter2_.chapter_name as chapter_3_1_3_, chapter2_.chapter_parent as chapter_4_1_3_, chapter2_.course as course8_1_3_, chapter2_.course_id as course_i5_1_3_, chapter2_.leaf as leaf6_1_3_, chapter2_.video_id as video_id9_1_3_ from t_chapter chapters0_ left outer join t_video video1_ on chapters0_.video_id=video1_.id left outer join t_chapter chapter2_ on video1_.chapter_key=chapter2_.id where chapters0_.course=?
		 * Hibernate: delete from t_course where id=?
		 */
//		Course course = cdao.findById(4l).get();
//		cdao.delete(course);
	}

	/**
	 * 测试course和chapter 一对多关系 CRUD
	 * 结论：同上
	 */
	@Test
//	@Rollback(value = false)
//	@Transactional
	public void courseAndChapter() {
//		chd.deleteAll();
		/**
		 * 保存chapter 1条保存SQL
		 * Hibernate: select course0_.id as id1_4_0_, course0_.course_cover as course_c2_4_0_, course0_.course_desc as course_d3_4_0_, course0_.course_findbytype as course_f4_4_0_, course0_.course_name as course_n5_4_0_, course0_.course_type as course_t6_4_0_, course0_.teacher as teacher8_4_0_, course0_.teacher_id as teacher_7_4_0_, teacher1_.id as id1_8_1_, teacher1_.certificate_id as certific5_8_1_, teacher1_.teacher_email as teacher_2_8_1_, teacher1_.teacher_name as teacher_3_8_1_, teacher1_.teacher_picture as teacher_4_8_1_ from t_course course0_ left outer join t_teacher teacher1_ on course0_.teacher=teacher1_.id where course0_.id=?
		 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
		 */
//		Chapter chapter = new Chapter();
//		chapter.setChapter_name("第一章");
//		Course course = cdao.findById(5l).get();
//		chapter.setCourse(course);
//		chd.save(chapter);
		
		/**
		 * 删除chapter
		 * Hibernate: select chapter0_.id as id1_1_0_, chapter0_.chapter_pid as chapter_7_1_0_, chapter0_.chapter_level as chapter_2_1_0_, chapter0_.chapter_name as chapter_3_1_0_, chapter0_.chapter_parent as chapter_4_1_0_, chapter0_.course as course8_1_0_, chapter0_.course_id as course_i5_1_0_, chapter0_.leaf as leaf6_1_0_, chapter0_.video_id as video_id9_1_0_, course1_.id as id1_4_1_, course1_.course_cover as course_c2_4_1_, course1_.course_desc as course_d3_4_1_, course1_.course_findbytype as course_f4_4_1_, course1_.course_name as course_n5_4_1_, course1_.course_type as course_t6_4_1_, course1_.teacher as teacher8_4_1_, course1_.teacher_id as teacher_7_4_1_, teacher2_.id as id1_8_2_, teacher2_.certificate_id as certific5_8_2_, teacher2_.teacher_email as teacher_2_8_2_, teacher2_.teacher_name as teacher_3_8_2_, teacher2_.teacher_picture as teacher_4_8_2_, video3_.id as id1_9_3_, video3_.chapter_key as chapter_6_9_3_, video3_.chapter_id as chapter_2_9_3_, video3_.course_id as course_i3_9_3_, video3_.video_name as video_na4_9_3_, video3_.video_src as video_sr5_9_3_, chapter4_.id as id1_1_4_, chapter4_.chapter_pid as chapter_7_1_4_, chapter4_.chapter_level as chapter_2_1_4_, chapter4_.chapter_name as chapter_3_1_4_, chapter4_.chapter_parent as chapter_4_1_4_, chapter4_.course as course8_1_4_, chapter4_.course_id as course_i5_1_4_, chapter4_.leaf as leaf6_1_4_, chapter4_.video_id as video_id9_1_4_ from t_chapter chapter0_ left outer join t_course course1_ on chapter0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on chapter0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where chapter0_.id=?
		 * Hibernate: select child_chap0_.chapter_pid as chapter_7_1_0_, child_chap0_.id as id1_1_0_, child_chap0_.id as id1_1_1_, child_chap0_.chapter_pid as chapter_7_1_1_, child_chap0_.chapter_level as chapter_2_1_1_, child_chap0_.chapter_name as chapter_3_1_1_, child_chap0_.chapter_parent as chapter_4_1_1_, child_chap0_.course as course8_1_1_, child_chap0_.course_id as course_i5_1_1_, child_chap0_.leaf as leaf6_1_1_, child_chap0_.video_id as video_id9_1_1_, course1_.id as id1_4_2_, course1_.course_cover as course_c2_4_2_, course1_.course_desc as course_d3_4_2_, course1_.course_findbytype as course_f4_4_2_, course1_.course_name as course_n5_4_2_, course1_.course_type as course_t6_4_2_, course1_.teacher as teacher8_4_2_, course1_.teacher_id as teacher_7_4_2_, teacher2_.id as id1_8_3_, teacher2_.certificate_id as certific5_8_3_, teacher2_.teacher_email as teacher_2_8_3_, teacher2_.teacher_name as teacher_3_8_3_, teacher2_.teacher_picture as teacher_4_8_3_, video3_.id as id1_9_4_, video3_.chapter_key as chapter_6_9_4_, video3_.chapter_id as chapter_2_9_4_, video3_.course_id as course_i3_9_4_, video3_.video_name as video_na4_9_4_, video3_.video_src as video_sr5_9_4_, chapter4_.id as id1_1_5_, chapter4_.chapter_pid as chapter_7_1_5_, chapter4_.chapter_level as chapter_2_1_5_, chapter4_.chapter_name as chapter_3_1_5_, chapter4_.chapter_parent as chapter_4_1_5_, chapter4_.course as course8_1_5_, chapter4_.course_id as course_i5_1_5_, chapter4_.leaf as leaf6_1_5_, chapter4_.video_id as video_id9_1_5_ from t_chapter child_chap0_ left outer join t_course course1_ on child_chap0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on child_chap0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where child_chap0_.chapter_pid=?
		 * Hibernate: delete from t_chapter where id=?
		 */
//		Chapter chapter = chd.findById(46l).get();
//		chd.delete(chapter);
		
		/**
		 * 删除course
		 * Hibernate: select course0_.id as id1_4_0_, course0_.course_cover as course_c2_4_0_, course0_.course_desc as course_d3_4_0_, course0_.course_findbytype as course_f4_4_0_, course0_.course_name as course_n5_4_0_, course0_.course_type as course_t6_4_0_, course0_.teacher as teacher8_4_0_, course0_.teacher_id as teacher_7_4_0_, teacher1_.id as id1_8_1_, teacher1_.certificate_id as certific5_8_1_, teacher1_.teacher_email as teacher_2_8_1_, teacher1_.teacher_name as teacher_3_8_1_, teacher1_.teacher_picture as teacher_4_8_1_ from t_course course0_ left outer join t_teacher teacher1_ on course0_.teacher=teacher1_.id where course0_.id=?
		 * Hibernate: select chapters0_.course as course8_1_0_, chapters0_.id as id1_1_0_, chapters0_.id as id1_1_1_, chapters0_.chapter_pid as chapter_7_1_1_, chapters0_.chapter_level as chapter_2_1_1_, chapters0_.chapter_name as chapter_3_1_1_, chapters0_.chapter_parent as chapter_4_1_1_, chapters0_.course as course8_1_1_, chapters0_.course_id as course_i5_1_1_, chapters0_.leaf as leaf6_1_1_, chapters0_.video_id as video_id9_1_1_, video1_.id as id1_9_2_, video1_.chapter_key as chapter_6_9_2_, video1_.chapter_id as chapter_2_9_2_, video1_.course_id as course_i3_9_2_, video1_.video_name as video_na4_9_2_, video1_.video_src as video_sr5_9_2_, chapter2_.id as id1_1_3_, chapter2_.chapter_pid as chapter_7_1_3_, chapter2_.chapter_level as chapter_2_1_3_, chapter2_.chapter_name as chapter_3_1_3_, chapter2_.chapter_parent as chapter_4_1_3_, chapter2_.course as course8_1_3_, chapter2_.course_id as course_i5_1_3_, chapter2_.leaf as leaf6_1_3_, chapter2_.video_id as video_id9_1_3_ from t_chapter chapters0_ left outer join t_video video1_ on chapters0_.video_id=video1_.id left outer join t_chapter chapter2_ on video1_.chapter_key=chapter2_.id where chapters0_.course=?
		 * Hibernate: select child_chap0_.chapter_pid as chapter_7_1_0_, child_chap0_.id as id1_1_0_, child_chap0_.id as id1_1_1_, child_chap0_.chapter_pid as chapter_7_1_1_, child_chap0_.chapter_level as chapter_2_1_1_, child_chap0_.chapter_name as chapter_3_1_1_, child_chap0_.chapter_parent as chapter_4_1_1_, child_chap0_.course as course8_1_1_, child_chap0_.course_id as course_i5_1_1_, child_chap0_.leaf as leaf6_1_1_, child_chap0_.video_id as video_id9_1_1_, course1_.id as id1_4_2_, course1_.course_cover as course_c2_4_2_, course1_.course_desc as course_d3_4_2_, course1_.course_findbytype as course_f4_4_2_, course1_.course_name as course_n5_4_2_, course1_.course_type as course_t6_4_2_, course1_.teacher as teacher8_4_2_, course1_.teacher_id as teacher_7_4_2_, teacher2_.id as id1_8_3_, teacher2_.certificate_id as certific5_8_3_, teacher2_.teacher_email as teacher_2_8_3_, teacher2_.teacher_name as teacher_3_8_3_, teacher2_.teacher_picture as teacher_4_8_3_, video3_.id as id1_9_4_, video3_.chapter_key as chapter_6_9_4_, video3_.chapter_id as chapter_2_9_4_, video3_.course_id as course_i3_9_4_, video3_.video_name as video_na4_9_4_, video3_.video_src as video_sr5_9_4_, chapter4_.id as id1_1_5_, chapter4_.chapter_pid as chapter_7_1_5_, chapter4_.chapter_level as chapter_2_1_5_, chapter4_.chapter_name as chapter_3_1_5_, chapter4_.chapter_parent as chapter_4_1_5_, chapter4_.course as course8_1_5_, chapter4_.course_id as course_i5_1_5_, chapter4_.leaf as leaf6_1_5_, chapter4_.video_id as video_id9_1_5_ from t_chapter child_chap0_ left outer join t_course course1_ on child_chap0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on child_chap0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where child_chap0_.chapter_pid=?
		 * Hibernate: update t_chapter set course=null where course=?
		 * Hibernate: delete from t_chapter where id=?
		 * Hibernate: delete from t_video where id=?
		 * Hibernate: delete from t_course where id=?
		 */
//		Course course = cdao.findById(3l).get();
		cs.deleteById(5l);
		
	}
	
	
	/**
	 * 测试chapter自身反射链接 树结构 的CRUD操作
	 * 结论 ：新增子节点时，先设置父节点在保存子节点 的SQL最简洁。
	 */
	@Test
	@Rollback(value = false)
	@Transactional
	public void chapter() {
//		Chapter chapter = chd.findById(4l).get();
		Chapter chapter2 = chd.findById(51l).get();
		
//		System.out.println(chapter2);
//		for (Chapter iterable_element : chapter2.getChild_chapters()) {
//			System.out.println(iterable_element);
//		}
//		
		for (int i = 1; i < 5; i++) {
			Chapter type = new Chapter();
			type.setChapter_name("第" + (i+5) + "节");
			type.setCourse_id(1l);
			type.setLeaf(false);
			/**
			 * 下面两语句顺序不能调换！！！（傻了，没开事务！）
			 * 游离态对象主动关联持久态对象后，再保存持久态对象会报错
			 * Caused by: javax.persistence.PersistenceException: org.hibernate.PersistentObjectException: detached entity passed to persist: com.iCourse.course.bean.Chapter
			 * ... 51 more
			 * Caused by: org.hibernate.PersistentObjectException: detached entity passed to persist: com.iCourse.course.bean.Chapter
			 * 要先把游离态对象保存为持久态，再关联另外的持久态对象
			 * 这种方式保存，父节点的值为空!!! 为啥呀？？？  丢，忘记开事务了！ 再调换测试一下！！！！
			 * chd.save(type);
			 * type.setChapter(chapter2);
			 * SQL语句： 先插入新数据，再更新父节点外键   效率有点低
			 * Hibernate: select chapter0_.id as id1_1_0_, chapter0_.chapter_pid as chapter_7_1_0_, chapter0_.chapter_level as chapter_2_1_0_, chapter0_.chapter_name as chapter_3_1_0_, chapter0_.chapter_parent as chapter_4_1_0_, chapter0_.course as course8_1_0_, chapter0_.course_id as course_i5_1_0_, chapter0_.leaf as leaf6_1_0_, chapter0_.video_id as video_id9_1_0_, course1_.id as id1_4_1_, course1_.course_cover as course_c2_4_1_, course1_.course_desc as course_d3_4_1_, course1_.course_findbytype as course_f4_4_1_, course1_.course_name as course_n5_4_1_, course1_.course_type as course_t6_4_1_, course1_.teacher as teacher8_4_1_, course1_.teacher_id as teacher_7_4_1_, teacher2_.id as id1_8_2_, teacher2_.certificate_id as certific5_8_2_, teacher2_.teacher_email as teacher_2_8_2_, teacher2_.teacher_name as teacher_3_8_2_, teacher2_.teacher_picture as teacher_4_8_2_, video3_.id as id1_9_3_, video3_.chapter_key as chapter_6_9_3_, video3_.chapter_id as chapter_2_9_3_, video3_.course_id as course_i3_9_3_, video3_.video_name as video_na4_9_3_, video3_.video_src as video_sr5_9_3_, chapter4_.id as id1_1_4_, chapter4_.chapter_pid as chapter_7_1_4_, chapter4_.chapter_level as chapter_2_1_4_, chapter4_.chapter_name as chapter_3_1_4_, chapter4_.chapter_parent as chapter_4_1_4_, chapter4_.course as course8_1_4_, chapter4_.course_id as course_i5_1_4_, chapter4_.leaf as leaf6_1_4_, chapter4_.video_id as video_id9_1_4_ from t_chapter chapter0_ left outer join t_course course1_ on chapter0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on chapter0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where chapter0_.id=?
			 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 * Hibernate: update t_chapter set chapter_pid=?, chapter_level=?, chapter_name=?, chapter_parent=?, course=?, course_id=?, leaf=?, video_id=? where id=?
			 * Hibernate: update t_chapter set chapter_pid=?, chapter_level=?, chapter_name=?, chapter_parent=?, course=?, course_id=?, leaf=?, video_id=? where id=?
			 * Hibernate: update t_chapter set chapter_pid=?, chapter_level=?, chapter_name=?, chapter_parent=?, course=?, course_id=?, leaf=?, video_id=? where id=?
			 * Hibernate: update t_chapter set chapter_pid=?, chapter_level=?, chapter_name=?, chapter_parent=?, course=?, course_id=?, leaf=?, video_id=? where id=?
			 *	
			 *     调换后也是可以的。。。。。
			 * type.setChapter(chapter2);
			 * chd.save(type);
			 * SQL语句：直接插入新数据完事。
			 * Hibernate: select chapter0_.id as id1_1_0_, chapter0_.chapter_pid as chapter_7_1_0_, chapter0_.chapter_level as chapter_2_1_0_, chapter0_.chapter_name as chapter_3_1_0_, chapter0_.chapter_parent as chapter_4_1_0_, chapter0_.course as course8_1_0_, chapter0_.course_id as course_i5_1_0_, chapter0_.leaf as leaf6_1_0_, chapter0_.video_id as video_id9_1_0_, course1_.id as id1_4_1_, course1_.course_cover as course_c2_4_1_, course1_.course_desc as course_d3_4_1_, course1_.course_findbytype as course_f4_4_1_, course1_.course_name as course_n5_4_1_, course1_.course_type as course_t6_4_1_, course1_.teacher as teacher8_4_1_, course1_.teacher_id as teacher_7_4_1_, teacher2_.id as id1_8_2_, teacher2_.certificate_id as certific5_8_2_, teacher2_.teacher_email as teacher_2_8_2_, teacher2_.teacher_name as teacher_3_8_2_, teacher2_.teacher_picture as teacher_4_8_2_, video3_.id as id1_9_3_, video3_.chapter_key as chapter_6_9_3_, video3_.chapter_id as chapter_2_9_3_, video3_.course_id as course_i3_9_3_, video3_.video_name as video_na4_9_3_, video3_.video_src as video_sr5_9_3_, chapter4_.id as id1_1_4_, chapter4_.chapter_pid as chapter_7_1_4_, chapter4_.chapter_level as chapter_2_1_4_, chapter4_.chapter_name as chapter_3_1_4_, chapter4_.chapter_parent as chapter_4_1_4_, chapter4_.course as course8_1_4_, chapter4_.course_id as course_i5_1_4_, chapter4_.leaf as leaf6_1_4_, chapter4_.video_id as video_id9_1_4_ from t_chapter chapter0_ left outer join t_course course1_ on chapter0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on chapter0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where chapter0_.id=?
			 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 * Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 * 
			 */
//			chd.save(type);
//			type.setChapter(chapter2);
			
			/**
			 *  提前设置ID属性
			 *  SQL语句
			 *  Hibernate: select chapter0_.id as id1_1_0_, chapter0_.chapter_pid as chapter_7_1_0_, chapter0_.chapter_level as chapter_2_1_0_, chapter0_.chapter_name as chapter_3_1_0_, chapter0_.chapter_parent as chapter_4_1_0_, chapter0_.course as course8_1_0_, chapter0_.course_id as course_i5_1_0_, chapter0_.leaf as leaf6_1_0_, chapter0_.video_id as video_id9_1_0_, course1_.id as id1_4_1_, course1_.course_cover as course_c2_4_1_, course1_.course_desc as course_d3_4_1_, course1_.course_findbytype as course_f4_4_1_, course1_.course_name as course_n5_4_1_, course1_.course_type as course_t6_4_1_, course1_.teacher as teacher8_4_1_, course1_.teacher_id as teacher_7_4_1_, teacher2_.id as id1_8_2_, teacher2_.certificate_id as certific5_8_2_, teacher2_.teacher_email as teacher_2_8_2_, teacher2_.teacher_name as teacher_3_8_2_, teacher2_.teacher_picture as teacher_4_8_2_, video3_.id as id1_9_3_, video3_.chapter_key as chapter_6_9_3_, video3_.chapter_id as chapter_2_9_3_, video3_.course_id as course_i3_9_3_, video3_.video_name as video_na4_9_3_, video3_.video_src as video_sr5_9_3_, chapter4_.id as id1_1_4_, chapter4_.chapter_pid as chapter_7_1_4_, chapter4_.chapter_level as chapter_2_1_4_, chapter4_.chapter_name as chapter_3_1_4_, chapter4_.chapter_parent as chapter_4_1_4_, chapter4_.course as course8_1_4_, chapter4_.course_id as course_i5_1_4_, chapter4_.leaf as leaf6_1_4_, chapter4_.video_id as video_id9_1_4_ from t_chapter chapter0_ left outer join t_course course1_ on chapter0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on chapter0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where chapter0_.id=?
			 *Hibernate: select chapter0_.id as id1_1_2_, chapter0_.chapter_pid as chapter_7_1_2_, chapter0_.chapter_level as chapter_2_1_2_, chapter0_.chapter_name as chapter_3_1_2_, chapter0_.chapter_parent as chapter_4_1_2_, chapter0_.course as course8_1_2_, chapter0_.course_id as course_i5_1_2_, chapter0_.leaf as leaf6_1_2_, chapter0_.video_id as video_id9_1_2_, chapter1_.id as id1_1_0_, chapter1_.chapter_pid as chapter_7_1_0_, chapter1_.chapter_level as chapter_2_1_0_, chapter1_.chapter_name as chapter_3_1_0_, chapter1_.chapter_parent as chapter_4_1_0_, chapter1_.course as course8_1_0_, chapter1_.course_id as course_i5_1_0_, chapter1_.leaf as leaf6_1_0_, chapter1_.video_id as video_id9_1_0_, video2_.id as id1_9_1_, video2_.chapter_key as chapter_6_9_1_, video2_.chapter_id as chapter_2_9_1_, video2_.course_id as course_i3_9_1_, video2_.video_name as video_na4_9_1_, video2_.video_src as video_sr5_9_1_ from t_chapter chapter0_ left outer join t_chapter chapter1_ on chapter0_.chapter_pid=chapter1_.id left outer join t_video video2_ on chapter1_.video_id=video2_.id where chapter0_.id=?
			 *Hibernate: select child_chap0_.chapter_pid as chapter_7_1_0_, child_chap0_.id as id1_1_0_, child_chap0_.id as id1_1_1_, child_chap0_.chapter_pid as chapter_7_1_1_, child_chap0_.chapter_level as chapter_2_1_1_, child_chap0_.chapter_name as chapter_3_1_1_, child_chap0_.chapter_parent as chapter_4_1_1_, child_chap0_.course as course8_1_1_, child_chap0_.course_id as course_i5_1_1_, child_chap0_.leaf as leaf6_1_1_, child_chap0_.video_id as video_id9_1_1_, course1_.id as id1_4_2_, course1_.course_cover as course_c2_4_2_, course1_.course_desc as course_d3_4_2_, course1_.course_findbytype as course_f4_4_2_, course1_.course_name as course_n5_4_2_, course1_.course_type as course_t6_4_2_, course1_.teacher as teacher8_4_2_, course1_.teacher_id as teacher_7_4_2_, teacher2_.id as id1_8_3_, teacher2_.certificate_id as certific5_8_3_, teacher2_.teacher_email as teacher_2_8_3_, teacher2_.teacher_name as teacher_3_8_3_, teacher2_.teacher_picture as teacher_4_8_3_, video3_.id as id1_9_4_, video3_.chapter_key as chapter_6_9_4_, video3_.chapter_id as chapter_2_9_4_, video3_.course_id as course_i3_9_4_, video3_.video_name as video_na4_9_4_, video3_.video_src as video_sr5_9_4_, chapter4_.id as id1_1_5_, chapter4_.chapter_pid as chapter_7_1_5_, chapter4_.chapter_level as chapter_2_1_5_, chapter4_.chapter_name as chapter_3_1_5_, chapter4_.chapter_parent as chapter_4_1_5_, chapter4_.course as course8_1_5_, chapter4_.course_id as course_i5_1_5_, chapter4_.leaf as leaf6_1_5_, chapter4_.video_id as video_id9_1_5_ from t_chapter child_chap0_ left outer join t_course course1_ on child_chap0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on child_chap0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where child_chap0_.chapter_pid=?
			 *Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 *Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 *Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 *Hibernate: update t_chapter set chapter_pid=?, chapter_level=?, chapter_name=?, chapter_parent=?, course=?, course_id=?, leaf=?, video_id=? where id=?
			 *  
			 */
			if(i==1) { 
				type.setId(69l);
				type.setChapter_name("已存在的id");
			}
			type.setChapter(chapter2);
			chd.save(type);
			
			
			/**
			 * 另外一种种保存方式 
			 * 比先 chd.save(type);后type.setChapter(chapter2);方式还要多一条查询SQL
			 * 多的一条查询SQL的作用？
			 *    
			 * from t_chapter child_chap0_ left outer join t_course course1_ on child_chap0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on child_chap0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id
			 * SQL语句 
			 * Hibernate: select chapter0_.id as id1_1_0_, chapter0_.chapter_pid as chapter_7_1_0_, chapter0_.chapter_level as chapter_2_1_0_, chapter0_.chapter_name as chapter_3_1_0_, chapter0_.chapter_parent as chapter_4_1_0_, chapter0_.course as course8_1_0_, chapter0_.course_id as course_i5_1_0_, chapter0_.leaf as leaf6_1_0_, chapter0_.video_id as video_id9_1_0_, course1_.id as id1_4_1_, course1_.course_cover as course_c2_4_1_, course1_.course_desc as course_d3_4_1_, course1_.course_findbytype as course_f4_4_1_, course1_.course_name as course_n5_4_1_, course1_.course_type as course_t6_4_1_, course1_.teacher as teacher8_4_1_, course1_.teacher_id as teacher_7_4_1_, teacher2_.id as id1_8_2_, teacher2_.certificate_id as certific5_8_2_, teacher2_.teacher_email as teacher_2_8_2_, teacher2_.teacher_name as teacher_3_8_2_, teacher2_.teacher_picture as teacher_4_8_2_, video3_.id as id1_9_3_, video3_.chapter_key as chapter_6_9_3_, video3_.chapter_id as chapter_2_9_3_, video3_.course_id as course_i3_9_3_, video3_.video_name as video_na4_9_3_, video3_.video_src as video_sr5_9_3_, chapter4_.id as id1_1_4_, chapter4_.chapter_pid as chapter_7_1_4_, chapter4_.chapter_level as chapter_2_1_4_, chapter4_.chapter_name as chapter_3_1_4_, chapter4_.chapter_parent as chapter_4_1_4_, chapter4_.course as course8_1_4_, chapter4_.course_id as course_i5_1_4_, chapter4_.leaf as leaf6_1_4_, chapter4_.video_id as video_id9_1_4_ from t_chapter chapter0_ left outer join t_course course1_ on chapter0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on chapter0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where chapter0_.id=?
			 *Hibernate: select child_chap0_.chapter_pid as chapter_7_1_0_, child_chap0_.id as id1_1_0_, child_chap0_.id as id1_1_1_, child_chap0_.chapter_pid as chapter_7_1_1_, child_chap0_.chapter_level as chapter_2_1_1_, child_chap0_.chapter_name as chapter_3_1_1_, child_chap0_.chapter_parent as chapter_4_1_1_, child_chap0_.course as course8_1_1_, child_chap0_.course_id as course_i5_1_1_, child_chap0_.leaf as leaf6_1_1_, child_chap0_.video_id as video_id9_1_1_, course1_.id as id1_4_2_, course1_.course_cover as course_c2_4_2_, course1_.course_desc as course_d3_4_2_, course1_.course_findbytype as course_f4_4_2_, course1_.course_name as course_n5_4_2_, course1_.course_type as course_t6_4_2_, course1_.teacher as teacher8_4_2_, course1_.teacher_id as teacher_7_4_2_, teacher2_.id as id1_8_3_, teacher2_.certificate_id as certific5_8_3_, teacher2_.teacher_email as teacher_2_8_3_, teacher2_.teacher_name as teacher_3_8_3_, teacher2_.teacher_picture as teacher_4_8_3_, video3_.id as id1_9_4_, video3_.chapter_key as chapter_6_9_4_, video3_.chapter_id as chapter_2_9_4_, video3_.course_id as course_i3_9_4_, video3_.video_name as video_na4_9_4_, video3_.video_src as video_sr5_9_4_, chapter4_.id as id1_1_5_, chapter4_.chapter_pid as chapter_7_1_5_, chapter4_.chapter_level as chapter_2_1_5_, chapter4_.chapter_name as chapter_3_1_5_, chapter4_.chapter_parent as chapter_4_1_5_, chapter4_.course as course8_1_5_, chapter4_.course_id as course_i5_1_5_, chapter4_.leaf as leaf6_1_5_, chapter4_.video_id as video_id9_1_5_ from t_chapter child_chap0_ left outer join t_course course1_ on child_chap0_.course=course1_.id left outer join t_teacher teacher2_ on course1_.teacher=teacher2_.id left outer join t_video video3_ on child_chap0_.video_id=video3_.id left outer join t_chapter chapter4_ on video3_.chapter_key=chapter4_.id where child_chap0_.chapter_pid=?
			 *Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 *Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 *Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 *Hibernate: insert into t_chapter (chapter_pid, chapter_level, chapter_name, chapter_parent, course, course_id, leaf, video_id) values (?, ?, ?, ?, ?, ?, ?, ?)
			 *Hibernate: update t_chapter set chapter_pid=? where id=?
			 *Hibernate: update t_chapter set chapter_pid=? where id=?
			 *Hibernate: update t_chapter set chapter_pid=? where id=?
			 *Hibernate: update t_chapter set chapter_pid=? where id=?
			 */
//			if(i==1) { //会报错  ：detached entity passed to persist
//				type.setId(67l);
//				type.setChapter_name("已存在的id");
//			}
//			chapter2.getChild_chapters().add(type);
		}

//
//		List<Chapter> child_chapters = chapter.getChild_chapters();
//
//		for (Chapter chapter3 : child_chapters) {
//			System.out.println(chapter3);
//			chapter3.setChapter(chapter2);
////			System.out.println(chapter3);
//			break;
//		}

//		chd.delete(chapter);


	}

	/**
	 * 测试chapter和video 一对一关系的 CRUD 结果：用chapter一方级联保存video一方 video上的外键会为空，反之一样。
	 * 如果，双方都设置对应关联的属性，在都保存，两边都会有外键，这也无论那一边要删除由于外键的原因都删不了， 实际业务中使用chapter级联保存video
	 * 让video的外键为空吧， 这样就只能通过chapter获取video，不能通过video获取chapter
	 * 删除chapter会级联删除video，而video不能单独删除，因为外键！。
	 */
	@Test
	@Rollback(value = false)
	@Transactional
	public void chapterVideo() {
		Video video = new Video();
//		Chapter chapter = chd.findById(28l).get();
		Chapter chapter = chd.findById(29l).get();
		video.setVideo_name("美洋洋");
//		chapter.setVideo(video);
//		chd.delete(chapter);
		video.setChapter(chapter);
		vdao.save(video);
//		Video video = vdao.findById(2l).get();
//		
//		vdao.delete(video);

	}

	
	/**
	 * 测试student和clazz  多对多关系
	 * 
	 */
	@Test
	@Rollback(value = false)
	@Transactional
	public void studentAndClazz() {
//		Clazz clazz = new Clazz();
//		clazz.setClazz_name("软件工程1班");
//		Student student = new Student();
//		student.setStudent_name("小明");
////		clazz.getStudents().add(student);
//		student.getClazzes().add(clazz);
		/**
		 * 使用clazz级联student
		 * Hibernate: insert into t_clazz (clazz_begin, clazz_end, clazz_name, clazz_serial_number, clazz_status, clazz_student_number, clazz_syn, course_id) values (?, ?, ?, ?, ?, ?, ?, ?)
		 *Hibernate: insert into t_student (certificate_id, password, student_name, student_phone, studetn_email, studetn_qq) values (?, ?, ?, ?, ?, ?)
		 *Hibernate: insert into t_clazz_student (clazz_id, student_id) values (?, ?)
		 */
//		cldao.save(clazz);
		/**
		 * 使用student 级联clazz效果一样
		 * Hibernate: insert into t_student (certificate_id, password, student_name, student_phone, studetn_email, studetn_qq) values (?, ?, ?, ?, ?, ?)	
		 *Hibernate: insert into t_clazz (clazz_begin, clazz_end, clazz_name, clazz_serial_number, clazz_status, clazz_student_number, clazz_syn, course_id) values (?, ?, ?, ?, ?, ?, ?, ?)
		 *Hibernate: insert into t_clazz_student (student_id, clazz_id) values (?, ?)
		 */
//		stdao.save(student);
		
		/**
		 * 删除学生  会删除student 和关联表 数据
		 * Hibernate: select student0_.id as id1_6_0_, student0_.certificate_id as certific7_6_0_, student0_.password as password2_6_0_, student0_.student_name as student_3_6_0_, student0_.student_phone as student_4_6_0_, student0_.studetn_email as studetn_5_6_0_, student0_.studetn_qq as studetn_6_6_0_, stucertifi1_.id as id1_5_1_, stucertifi1_.clazzname as clazznam2_5_1_, stucertifi1_.education as educatio3_5_1_, stucertifi1_.name as name4_5_1_, stucertifi1_.school as school5_5_1_, stucertifi1_.student_id as student_6_5_1_, student2_.id as id1_6_2_, student2_.certificate_id as certific7_6_2_, student2_.password as password2_6_2_, student2_.student_name as student_3_6_2_, student2_.student_phone as student_4_6_2_, student2_.studetn_email as studetn_5_6_2_, student2_.studetn_qq as studetn_6_6_2_ from t_student student0_ left outer join t_stu_certificate stucertifi1_ on student0_.certificate_id=stucertifi1_.id left outer join t_student student2_ on stucertifi1_.student_id=student2_.id where student0_.id=?
		 *Hibernate: delete from t_clazz_student where student_id=?
		 *Hibernate: delete from t_student where id=?
		 */
//		stdao.deleteById(2l);
		
		/**
		 * 删除clazz 一样
		 * Hibernate: select clazz0_.id as id1_2_0_, clazz0_.clazz_begin as clazz_be2_2_0_, clazz0_.clazz_end as clazz_en3_2_0_, clazz0_.clazz_name as clazz_na4_2_0_, clazz0_.clazz_serial_number as clazz_se5_2_0_, clazz0_.clazz_status as clazz_st6_2_0_, clazz0_.clazz_student_number as clazz_st7_2_0_, clazz0_.clazz_syn as clazz_sy8_2_0_, clazz0_.course_id as course_i9_2_0_ from t_clazz clazz0_ where clazz0_.id=?
		 *Hibernate: delete from t_clazz_student where clazz_id=?
		 *Hibernate: delete from t_clazz where id=?
		 */
		
		cldao.deleteById(1l);
		
		
	}
	
	/**
	 * QueryDTO测试
	 */
	@Test
	@Rollback(value = false)
	@Transactional
	public void findCourseChapter() {
		ChapterQueryDTO qDto = new ChapterQueryDTO();
		qDto.setCourse_id(2l);

		List<Chapter> chapter = chas.findAll(qDto.getWhere(qDto));

		for (Chapter chapter2 : chapter) {
			System.out.println(chapter2);
		}
	}
	
	
	@Test
	@Rollback(value = false)
	@Transactional
	public void addCategory() {
		Category type = new Category();

		type.setCategory_name("计算机");
		type.setCategory_key("computer");

		cd.save(type);

//		String find = "";
//		find += String.valueOf(type.getId()) + "_";
//		type.setCategory_find(find);

	}

	@Test
	@Rollback(value = false)
	@Transactional
	public void findCategory() {
		Iterable<Category> findAll = cd.findAll();
		List<Category> a = (List<Category>) findAll;
		for (Category category : a) {

		}

		Iterator<Category> iterator = findAll.iterator();
		System.out.println(iterator.getClass());
	}
	
	@Test
	@Rollback(value = false)
	@Transactional
	public void demo1() {
		List<Teacher> re = ts.findTeacherAndCourse();
		for (Teacher teacher : re) {
			System.out.println(teacher);
		}
	}
	
	@Test
	@Rollback(value = false)
	@Transactional
	public void demo2() {
		int pageNumber = 1;
		int size = 1;
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable =  PageRequest.of(pageNumber, size, sort);
		/**
		 * Hibernate: SELECT * from t_course where teacher = ? order by id desc limit ?, ?
		 *Hibernate: select teacher0_.id as id1_8_0_, teacher0_.certificate_id as certific5_8_0_, teacher0_.teacher_email as teacher_2_8_0_, teacher0_.teacher_name as teacher_3_8_0_, teacher0_.teacher_picture as teacher_4_8_0_ from t_teacher teacher0_ where teacher0_.id=?
		 *Course [course_name=java, course_type=computer, course_desc=基础课, course_cover=/userImage/86VEI(3M4`C47{EG8M70G6L.png, course_findbytype=null, teacher_id=1]
		 */
		List<Course>re = cdao.findCourseByTeacherId(1l,pageable);
		for (Course course : re) {
			System.out.println(course);
		}
		
	}

}
