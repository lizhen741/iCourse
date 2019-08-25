package com.iCourse.user.service;

import java.io.InputStream;

import com.iCourse.user.bean.Account;

public interface AccountService {
	public String addMultipleStudentByFile(InputStream is,Long teacher_id);

	public Account login(Account account);

	public Account findById(long l);

	public Account findbyEmail(String mailStr);

	public void save(Account account);

	public void settingInfo(Account account);

	public void settingHeadPhoto(Account account);
}

