package com.iCourse.user.web.controller.student;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iCourse.common.util.AJAXresult;

@Controller
@RequestMapping("/student/upload")
@CrossOrigin
public class FileUploadStuController {

	@RequestMapping("/image")
	@ResponseBody
	public Object uploadImage(HttpServletRequest req) {
		MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
		AJAXresult<Object> result =new AJAXresult<Object>();
		
		
		try {
			MultipartFile file = request.getFile("file");
			String path = req.getSession().getServletContext().getRealPath("images");
			String originalFilename = file.getOriginalFilename();
			int index = originalFilename.lastIndexOf(".");
			String end = originalFilename.substring(index,originalFilename.length());
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMddhhmmss");
			String str = sdf.format(new Date());
			String fileName=str+end;
			file.transferTo(new File(path +"/"+ fileName));
			
			
			
			result.setFileUrl("/images/"+ fileName);
			result.setOk(true);
		} catch (Exception e) {
			result.setOk(false);
		}
		
		return result;
	}
}
