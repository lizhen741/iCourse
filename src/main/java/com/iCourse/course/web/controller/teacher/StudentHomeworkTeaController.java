package com.iCourse.course.web.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.Homework;
import com.iCourse.course.bean.StudentHomework;
import com.iCourse.course.bean.DTO.StudentHomeworkQueryDTO;
import com.iCourse.course.service.HomeworkService;
import com.iCourse.course.service.StudentHomeworkService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class StudentHomeworkTeaController extends BaseController<StudentHomework> {

	@Autowired
	private StudentHomeworkService studentHomeworkService;
	

	/**
	 * 查看具体发布的某次作业时，获取与该作业相关的学生信息
	 * @param query
	 * @param pageInfo
	 * @return
	 */
	@PostMapping("/viewStudentHomeworks")
	@ResponseBody
	public Object getStudentHomeworks(StudentHomeworkQueryDTO query, PageInfo<StudentHomework> pageInfo) {
		start();

		pageInfo.initPage(1, 8);
		Page<StudentHomework> data = studentHomeworkService.getStudentHomeworks(query.getWhere(query),
				pageInfo.getPageable());
		pageInfo.setReault(data);

		success(pageInfo);

		return end();
	}
	
	@GetMapping("/getOneStudentHomework")
	@ResponseBody
	public Object getStudentHomework(Long stuHomework_id) {
		start();
		
		StudentHomework studentHomework = studentHomeworkService.findById(stuHomework_id);
		studentHomework.setHomeworkissued_link_clazz(null);
		success(studentHomework);
		
		return end();
	}
	
	/**
	 * 教师给客观题打分
	 * @param id  StudentAnswerRecord的id
	 * @param grade
	 * @return
	 */
	@PostMapping("/setStudentAnswerRecordGrade")
	@ResponseBody
	public Object setStudentAnswerRecordGrade(Long id,Integer grade) {
		start();
		
		studentHomeworkService.setStudentAnswerRecordGrade(id,grade);
		
		success();
		return end();
	}
	
	/**
	 * 教师批改完成后设置学生本次作业的总分
	 * @param stuHomeword_id
	 * @param totalGrade
	 * @return
	 */
	@PostMapping("/setStuHomeworkTotalGrade/{stuHomeword_id}")
	@ResponseBody
	public Object setStuHomeworkTotalGrade(@PathVariable Long stuHomeword_id,Integer totalGrade) {
		start();
		
		studentHomeworkService.setStuHomeworkTotalGrade(stuHomeword_id,totalGrade);
		return end();
	}
}
