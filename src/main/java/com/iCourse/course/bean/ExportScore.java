package com.iCourse.course.bean;

import java.util.HashMap;
import java.util.Map;

//导出成绩类
public class ExportScore {

	private String cl_name;				//班级名
	private String stu_name;			//姓名
	private Map<String,String> scores = new HashMap<String,String>();	//（作业名称，成绩）
	private Integer totalScore;			//总成绩
	public String getCl_name() {
		return cl_name;
	}
	public void setCl_name(String cl_name) {
		this.cl_name = cl_name;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public Map<String, String> getScores() {
		return scores;
	}
	public void setScores(Map<String, String> scores) {
		this.scores = scores;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
}
