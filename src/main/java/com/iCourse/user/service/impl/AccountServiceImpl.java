package com.iCourse.user.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.iCourse.user.bean.Account;
import com.iCourse.user.bean.StuCertificate;
import com.iCourse.user.bean.Student;
import com.iCourse.user.bean.TeacherStudentLib;
import com.iCourse.user.dao.AccountDao;
import com.iCourse.user.dao.StudentDao;
import com.iCourse.user.dao.TeacherStudentLibDao;

@Service
@Transactional
public class AccountServiceImpl implements com.iCourse.user.service.AccountService {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private TeacherStudentLibDao teacherStudentLibDao;
	
	@Override
	public String addMultipleStudentByFile(InputStream is,Long teacher_id) {
		String message = "信息添加成功！";
		XSSFWorkbook  wb = null;
		try {
			wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			if(!"学生信息表".equals(sheet.getSheetName())){
				message = "工作表名称不正确！";
				return message;
			}
			//读取数据
			//最后一行的行号
			int lastRow = sheet.getLastRowNum();
			Student student = null;
			Account account = null;
			StuCertificate stuCertificate = null;
			for (int i = 1; i <= lastRow; i++) {
				account = new Account();
				sheet.getRow(i).getCell(0).setCellType(XSSFCell.CELL_TYPE_STRING);
				account.setAccount_num(sheet.getRow(i).getCell(0).getStringCellValue());
				//根据学号判断学生是否已存在
				String account_no = sheet.getRow(i).getCell(0).getStringCellValue();
				if(StringUtils.isEmpty(account_no)) {
					continue;
				}
				List<Account> list = accountDao.findAccountByStudentNum(account_no);
				if (list.size() > 0) {
					//若学号已存在跟新学生信息
					account = list.get(0);
					student = account.getStudent();
					stuCertificate = student.getCertificate();
				}
				else {
					student =new Student();
					stuCertificate = new StuCertificate();
				}
				stuCertificate.setStudent_num(account_no);
				student.setStudent_name(sheet.getRow(i).getCell(1).getStringCellValue());
				stuCertificate.setName(student.getStudent_name());
				stuCertificate.setEducation(sheet.getRow(i).getCell(2).getStringCellValue());
				stuCertificate.setSchool(sheet.getRow(i).getCell(3).getStringCellValue());
				stuCertificate.setClazzname(sheet.getRow(i).getCell(4).getStringCellValue());
				sheet.getRow(i).getCell(5).setCellType(XSSFCell.CELL_TYPE_STRING);
				sheet.getRow(i).getCell(6).setCellType(XSSFCell.CELL_TYPE_STRING);
				student.setStudent_phone(sheet.getRow(i).getCell(5).getStringCellValue());
				account.setPassword(sheet.getRow(i).getCell(6).getStringCellValue());
				student.setCertificate(stuCertificate);
				
				
				if(list.size() == 0) {
					account.setStatus(1);
//					s.setAccount(account);
					account.setStudent(student);
					accountDao.save(account);
				}
				
				TeacherStudentLib teacherStudentLib = new TeacherStudentLib();
				teacherStudentLib.setTeacher_id(teacher_id);
				teacherStudentLib.setStudent(student);
				teacherStudentLibDao.save(teacherStudentLib);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != wb) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return message;
	}

	@Override
	public Account login(Account account) {
		return accountDao.findByAccount(account.getAccount_num());
		
	}

	@Override
	public Account findById(long id) {
		return accountDao.findById(id).get();
	}

	@Override
	public Account findbyEmail(String mailStr) {
		return accountDao.findbyEmail(mailStr);
	}

	@Override
	public void save(Account account) {
		accountDao.save(account);
		
	}
	@Override
	public void settingInfo(Account account) {
		Account account2 = accountDao.findById(account.getId()).get();
		if(!StringUtils.isEmpty(account.getName())) {
			account2.setName(account.getName());
		}
		if(!StringUtils.isEmpty(account.getQq())) {
			account2.setName(account.getQq());
		}
	}

	@Override
	public void settingHeadPhoto(Account account) {
		accountDao.settingHeadPhoto(account.getId(),account.getHead_photo());
	}

}
