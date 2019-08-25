package com.iCourse.common.util;

import java.util.List;

import com.iCourse.common.bean.PageInfo;

public abstract class BaseController<T> {
	ThreadLocal<AJAXresult<T>> localresult = new ThreadLocal<AJAXresult<T>>();
	
	protected void start() {
		AJAXresult<T> result = new AJAXresult<T>();
		localresult.set(result);
	}

	protected AJAXresult<T> end() {
		AJAXresult<T> result = localresult.get();
		localresult.remove();
		return result;
	}
	
	protected void success() {
		localresult.get().setOk(true);
	}
	protected void success(PageInfo<T> pageInfo) {
		localresult.get().setPageObj(pageInfo);
		localresult.get().setOk(true);
	}
	protected void success(List<T> datas) {
		localresult.get().setResultList(datas);
		localresult.get().setOk(true);
	}
	protected void successObjList(List<Object> datas) {
		localresult.get().setObjDatas(datas);
		localresult.get().setOk(true);
	}
	protected void successObj(Object obj) {
		localresult.get().setObj(obj);
		localresult.get().setOk(true);
	}
	protected void success(T entity) {
		localresult.get().setEntity(entity);
		localresult.get().setOk(true);
	}
	
	protected void fail() {
		localresult.get().setOk(false);
	}
	
	protected void fail(String message) {
		localresult.get().setOk(false);
		localresult.get().setMessage(message);
	}
}
