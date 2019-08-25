package com.iCourse.common.util;

import java.util.List;

import com.iCourse.common.bean.PageInfo;

public class AJAXresult<T> {
	private boolean ok;
	private PageInfo<T> pageObj;
	private List<T> resultList;
	private List<Object> objDatas;
	private T entity;
	private Object obj;
	private String message;
	
	
	
	private String fileUrl;
	private String fileName;
	private Long new_chapter_id;
	
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public PageInfo<T> getPageObj() {
		return pageObj;
	}
	public void setPageObj(PageInfo<T> pageObj) {
		this.pageObj = pageObj;
	}
	public boolean isOk() {
		return ok;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public String getFileName() {
		return fileName;
	}
	public List<Object> getObjDatas() {
		return objDatas;
	}
	public void setObjDatas(List<Object> objDatas) {
		this.objDatas = objDatas;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getNew_chapter_id() {
		return new_chapter_id;
	}
	public void setNew_chapter_id(Long new_chapter_id) {
		this.new_chapter_id = new_chapter_id;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
