package com.iCourse.user.web.controller.teacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.Const;
import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.AJAXresult;
import com.iCourse.common.util.BaseController;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.StuCertificate;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.DTO.StudentQueryDTO;
import com.iCourse.user.dao.TeacherDao;
import com.iCourse.user.service.AccountService;
import com.iCourse.user.service.StudentService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class StudentTeaController extends BaseController<Student> {
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private AccountService accountService;

//	@PostMapping("/student/clazz")
//	@ResponseBody
//	public Object studentJionClazz(Clazz clazz) {
//		AJAXresult<Student> result = new AJAXresult<Student>();
//
//		Student student = studentService.findById(1l);
//
//		studentService.joinClazz(student, clazz.getId());
//
//		return result;
//	}

	/**
	 * 教师添加未注册新学生到课堂，自动注册学生账号
	 * 
	 * @param certificate
	 * @param clazz_id
	 * @return
	 */
	@PostMapping("/student/clazz/teacher")
	@ResponseBody
	public Object teacherAddStudent(StuCertificate certificate, Long clazz_id, HttpServletRequest req) {
		AJAXresult<Student> result = new AJAXresult<Student>();

		Account accountTeacher = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		certificate.setSchool(accountTeacher.getTeacher().getCertificate().getSchool());

		Student student = new Student();
		student.setStudent_name(certificate.getName());
		student.setCertificate(certificate);
		Account account = new Account();
		account.setAccount_num(certificate.getStudent_num());
		account.setPassword("123456");
		accountService.save(account);

		studentService.joinClazz(student, clazz_id);

		result.setOk(true);
		return result;
	}

	/**
	 * 教师获取曾经加入过他的课堂的学生列表 用于添加学生到一个新的课堂
	 * 过滤掉已经在该课堂的学生
	 * @param query
	 * @param pageInfo
	 * @param req
	 * @return
	 */
	@PostMapping("/getTeacherStudentLib")
	@ResponseBody
	public Object getTeacherStudentLib(StudentQueryDTO query, PageInfo<Student> pageInfo, HttpServletRequest req) {
		start();

		Account accountTeacher = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		/*
		 * 设置登录教师id
		 */
		query.setTeacher_id(1l);
		pageInfo.initPage(1, 8);
		Map<String, Object> map = studentService.getTeacherStudentLib(query,pageInfo.getPageable());
		
		List<Student> data = (List<Student>) map.get("data");
		pageInfo.setDatas(data);
		pageInfo.setPagesize((Integer) map.get("total"));
		pageInfo.setTotalpage((Integer) map.get("totalpage")+1);
		pageInfo.setTotalsize((Long) map.get("totalsize"));
		success(pageInfo);
		
		return end();
	}
	
	
	

	/**
	 * 教师获取课堂的学生列表
	 * 
	 * @param clazz_id
	 * @param pageInfo
	 * @return
	 */
	@GetMapping("/students/byclazz/{clazz_id}")
	@ResponseBody
	public Object getStudentByClazz(@PathVariable Long clazz_id, PageInfo<Student> pageInfo, HttpServletRequest req) {
		start();

//		Account accountTeacher = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);

		pageInfo.initPage(1, 20);
		Page<Student> data = studentService.findStudentByClazz(clazz_id, pageInfo.getPageable());
		
		pageInfo.setReault(data);
		success(pageInfo);

		return end();
	}

	/**
	 * 教师批量导入学生
	 * 
	 * @param req
	 * @param file_name
	 * @return
	 */
	@PostMapping("/addMultipleStudentByFile")
	@ResponseBody
	public Object addMultipleStudentByFile(HttpServletRequest req, String file_name) {
		start();
		
		Account accountTeacher = (Account) req.getSession().getAttribute(Const.LOGIN_TEACHER);
		
		String message = "";
		// FilenameUtils fileUtils = new FilenameUtils();
		String path = req.getSession().getServletContext().getRealPath("tempfile");
		File dir = new File(path);
		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			if (file.isFile()) {
				try {
//					if("application/vnd.ms-excel".equals(FileUtils.getFileType(new FileInputStream(file)))) {
					if (file.getName().equals(file_name)) {
						System.out.println(file.getAbsolutePath());
						// 调用service添加学生
						message = accountService.addMultipleStudentByFile(new FileInputStream(file),accountTeacher.getTeacher().getId());
					}
//					}
				} catch (FileNotFoundException e) {
					System.out.println("上传文件必须是excel!");
					e.printStackTrace();
				}
			}
		}

		return end();
	}
}
