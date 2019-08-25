package com.iCourse.course.web.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.util.AJAXresult;
import com.iCourse.course.bean.Video;
import com.iCourse.course.service.VideoService;

@Controller
@RequestMapping("/student")
@CrossOrigin
public class VideoStuController {

	@Autowired
	private VideoService videoService;
	
	@PostMapping("/video")
	@ResponseBody
	public Object addVideo(Video video) {
		AJAXresult<Video> result = new AJAXresult<Video>();
		
		videoService.save(video);
		
		result.setOk(true);
		return result;
	}
}
