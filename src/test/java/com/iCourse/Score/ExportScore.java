package com.iCourse.Score;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.iCourse.BaseTest;
import com.iCourse.course.dao.ClazzDao;
import com.iCourse.course.dao.ExportScoreDao;
import com.iCourse.user.service.ScoreService;


public class ExportScore  extends BaseTest{
	
	@Autowired
	private ScoreService exportScore;
	//@Test
	public void TestExportScore() {
		exportScore.exportScore(1L);
	}
	
	//@Test
	public void test() {
		List<String> a = new ArrayList<String>();
		a.add("1");a.add("2");
		for (int i = 0; i < 3; i++) {
			if(!"".equals(a.get(i).toString()))
				System.out.println(a.get(i).toString());
			else
				System.out.println("0");
		}
	}
	
	@Autowired
	private ClazzDao clazzDao;
	//@Test
	public void TestGetClazzById() {
		System.out.println(clazzDao.findById(2L).get());
	}
	@Autowired
	private ExportScoreDao exportScoreDao;
	//@Test
	public void TestCountHomework() {
		System.out.println(exportScoreDao.countHomeworkByCl_id(2L));
	}
	
	//@Test
	public void TestGetScoresByStuId() {
		System.out.println(exportScoreDao.getScoresByStuId(2L));
	}
	
	@Test
	public void TestUpdateGrade() {
		exportScoreDao.updateGrade();
	}
	
}
