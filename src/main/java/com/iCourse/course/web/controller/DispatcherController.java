package com.iCourse.course.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class DispatcherController {
	
	
	@RequestMapping("/index")
	public String welcome() {
		return "manage.html";
	}
	@RequestMapping("/manager")
	public String welcome2() {
		return "manage.html";
	}
	
}
