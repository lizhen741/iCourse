package com.iCourse.user.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iCourse.course.bean.Clazz;
import com.iCourse.course.bean.ExportScore;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.ExportScoreDao;
import com.iCourse.user.bean.Student;
import com.iCourse.user.service.ScoreService;

@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {
	
	@Autowired
	private ClazzDao clazzDao;
	@Autowired
	private ExportScoreDao scoreDao;
	
	@Override
	public void exportScore(Long cl_id) {
		//1.根据班级号获取班级信息
		Clazz clazz = clazzDao.findById(cl_id).get();
		//获取当前班级作业数
		Integer countHomework = scoreDao.countHomeworkByCl_id(cl_id);
		
		List<ExportScore> scoreList = new ArrayList<ExportScore>();
		
		//将班级中所有成绩为空的作业项置为0
		//scoreDao.updateGrade();
		
		//2.根据班级号获取学生集合
		List<Student> students = clazz.getStudents();
		for (Student student : students) {
			ExportScore temp = new ExportScore();
			List<Integer> scores = scoreDao.getScoresByStuId(student.getId());
			for (int i = 1; i <= scores.size(); i++) {
				temp.getScores().put("作业"+i, scores.get(i-1).toString());
			}
			temp.setStu_name(student.getStudent_name());
			scoreList.add(temp);
		}
		
	
		//创建一个工作簿
		HSSFWorkbook wk = new HSSFWorkbook();
		//创建一个工作表
		HSSFSheet sheet1 = wk.createSheet("16软件1班");
		//创建一行，行索引从0开始
		int count = 0;
		HSSFRow row1 = sheet1.createRow(0);
		count++;
		//设置列宽
		sheet1.setColumnWidth(0, 5000);  //with跟字体有关，字符大小*256
		//创建单元格，列索引从0开始
		HSSFCell cell1 = row1.createCell(0);
		//给单元格赋值
		Integer headLength = scoreList.get(1).getScores().size();
		
		//表格导航
		row1.createCell(0).setCellValue(" ");
		int i = 1;
		for (i = 1; i <= headLength; i++) {
			row1.createCell(i).setCellValue("作业"+i);
		}
		row1.createCell(i).setCellValue("总分");
		
		//表格正文
		for (int k = 0; k < scoreList.size(); k++) {
			int sum = 0;
			int j = 1;
			HSSFRow row = sheet1.createRow(count++);
			row.createCell(0).setCellValue(scoreList.get(k).getStu_name());
			for (j = 1; j <= scoreList.get(k).getScores().size(); j++) {
				row.createCell(j).setCellValue(scoreList.get(k).getScores().get("作业"+j));
				sum += Integer.parseInt(scoreList.get(k).getScores().get("作业"+j));
			}
			//计算总分
			row.createCell(j).setCellValue(sum);
		}
		
		
		File file = new File("C:\\Users\\pc\\Desktop\\1.xls");
		
		try {
			//保存文件
			wk.write(file);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
