package com.iCourse.course.web.controller.teacher;

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

import com.iCourse.common.bean.FileInfo;
import com.iCourse.common.util.AJAXresult;
import com.iCourse.common.util.BaseController;

@Controller
@RequestMapping("/teacher/upload")
@CrossOrigin
public class FileUploadTeaController extends BaseController<FileInfo>{

	@RequestMapping("/image")
	@ResponseBody
	public Object uploadImage(HttpServletRequest req) {
		MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
		FileInfo fi= new FileInfo(); 
		start();
		
		try {
			MultipartFile file = request.getFile("image");
			String path = req.getSession().getServletContext().getRealPath("images");
			String originalFilename = file.getOriginalFilename();
			int index = originalFilename.lastIndexOf(".");
			String end = originalFilename.substring(index,originalFilename.length());
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMddhhmmss");
			String str = sdf.format(new Date());
			String fileName=str+end;
			file.transferTo(new File(path +"/"+ fileName));
			
			fi.setFile_url("/images/"+ fileName);
			
			success(fi);
			
		} catch (Exception e) {
			fail();
		}
		
		return end();
	}
	@RequestMapping("/video")
	@ResponseBody
	public Object uploadVideo(HttpServletRequest req) {
		MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;

		FileInfo fi= new FileInfo(); 
		start();
		
		try {
			MultipartFile file = request.getFile("file");
			String path = req.getSession().getServletContext().getRealPath("video");
			String originalFilename = file.getOriginalFilename();
			int index = originalFilename.lastIndexOf(".");
			String end = originalFilename.substring(index,originalFilename.length());
			SimpleDateFormat sdf = new SimpleDateFormat("MMddhhmmss");
			String str = sdf.format(new Date());
			String fileName=str+end;
			file.transferTo(new File(path +"/"+ fileName));
			
			fi.setFile_name(file.getOriginalFilename());
			fi.setFile_url("/video/"+ fileName);
			success(fi);
		} catch (Exception e) {
			fail();
		}
		
		return end();
	}
	
	@RequestMapping("/commonFile")
	@ResponseBody
	public Object uploadCommonFile(HttpServletRequest req) {
		MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;

		FileInfo fi= new FileInfo(); 
		start();
		
		try {
			MultipartFile file = request.getFile("file");
			String path = req.getSession().getServletContext().getRealPath("tempfile");
			String originalFilename = file.getOriginalFilename();
			int index = originalFilename.lastIndexOf(".");
			String end = originalFilename.substring(index,originalFilename.length());
			SimpleDateFormat sdf = new SimpleDateFormat("MMddhhmmss");
			String str = sdf.format(new Date());
			String fileName=str+end;
			file.transferTo(new File(path +"/"+ fileName));
			
			fi.setFile_name(fileName);
			fi.setFile_url("/tempfile/"+ fileName);
			success(fi);
		} catch (Exception e) {
			fail();
		}
		
		return end();
	}
}
