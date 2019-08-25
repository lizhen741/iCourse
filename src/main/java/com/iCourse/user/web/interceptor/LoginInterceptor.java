package com.iCourse.user.web.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri = request.getRequestURI();
		
		Set<String> whiteListSet = new HashSet<String>();
		whiteListSet.add("/login.htm");
		
		if(whiteListSet.contains(uri)) {
			return true;
		}
		else {
			HttpSession session = request.getSession();
//			User userLogin = (User) session.getAttribute("userLogin");
//			
//			if(userLogin ==null) {
//				return false;
//			}
			return true;
		}
		
		
	}
	
}
