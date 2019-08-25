package com.iCourse.Account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.iCourse.BaseTest;
import com.iCourse.common.util.FileUtils;
import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.StuCertificate;
import com.iCourse.user.bean.Student;
import com.iCourse.user.dao.AccountDao;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.service.AccountService;

public class TestAccount extends BaseTest {
	@Autowired
	private AccountDao acc;
	@Autowired
	private AccountService Aservice;
	@Autowired
	private StudentDao scc;

	//@Test
	public void addAccount() {
		Student s = new Student();
		s.setStudent_name("王武");
		s.setStudent_phone("123456");
		StuCertificate st = new StuCertificate();
		st.setEducation("本科");
		st.setName("王武");
		st.setSchool("dgut");
		st.setClazzname("软件1班");
		s.setCertificate(st);
		Account account = new Account();
		account.setAccount_num("2016414041");
		account.setPassword("123456");
		account.setStatus(1);
		account.setTeacher(null);
		s.setAccount(account);
		account.setStudent(s);
		acc.save(account);
	}

	// @Test
	public void demo() {
		scc.deleteById(26l);
	}

	@Test
	public void file() {
		
		File dir = new File("C:\\Users\\pc\\Desktop\\");

		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			if (file.isFile()) {
				try {   
					//if("application/vnd.ms-excel".equals(FileUtils.getFileType(new FileInputStream(file)))) {
						if (file.getName().equals("11.xlsx")) {
							System.out.println(file.getAbsolutePath());
							//调用service添加学生
							Aservice.addMultipleStudentByFile(new FileInputStream(file),1l);
						}
					//}
				} catch (FileNotFoundException e) {
					System.out.println("上传文件必须是excel!");
					e.printStackTrace();
				}
			}
		}
	}
}
