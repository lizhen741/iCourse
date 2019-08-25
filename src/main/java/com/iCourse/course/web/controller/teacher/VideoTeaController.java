package com.iCourse.course.web.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.util.AJAXresult;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Video;
import com.iCourse.course.service.VideoService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class VideoTeaController extends BaseController<Video>{

	@Autowired
	private VideoService videoService;
	
	@PostMapping("/video")
	@ResponseBody
	public Object addVideo(Video video) {
		start();
		
		videoService.save(video);
		
		success();
		return end();
	}
}
