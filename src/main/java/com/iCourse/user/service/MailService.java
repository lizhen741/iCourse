package com.iCourse.user.service;

import com.iCourse.user.bean.Mail;

public interface MailService {
	public Mail sendEMail(Mail mail);
	public boolean isOutofdeafTime(String beginStr);
}
