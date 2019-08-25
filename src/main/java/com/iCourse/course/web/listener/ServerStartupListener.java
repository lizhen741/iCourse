package com.iCourse.course.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//将web应用路径保存在application范围中
		ServletContext application = sce.getServletContext();
		String appPath = application.getContextPath();
		application.setAttribute("APP_PATH", appPath);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	
}
