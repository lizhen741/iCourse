package com.iCourse.common.bean;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.iCourse.course.bean.Clazz;

public class PageInfo<T> {
	private List<T> datas;
	private Integer pageno; // 页码
	private Integer pagesize; // 一页数据条数
	private Integer totalpage; // 总页数
	private Long totalsize; // 数据条数

	private String descOrAsc;
	private String sortby;

	private Sort sort;
	private Pageable pageable;

	
	public void initPage(int defaultNo,int defaultSize) {
		if (getPageno() == null || getPageno() < 1) {
			setPageno(defaultNo);
		}
		if (getPagesize() == null || getPagesize() < 1) {
			setPagesize(defaultSize);
		}
		if (getSortby() == null || getSortby() == "" || getSortby().isEmpty()) {
			setSortby("id");
		}
		if (!StringUtils.isEmpty(getDescOrAsc()) && "asc".equals(getDescOrAsc())) {
			sort = Sort.by(Sort.Direction.ASC, getSortby());
			setDescOrAsc("asc");
		} else {
			sort = Sort.by(Sort.Direction.DESC, getSortby());
			setDescOrAsc("desc");
		}
		
		this.pageable = PageRequest.of(getPageno() - 1, getPagesize(), getSort());
	}
	
//	public pageInfo() {
//		this.pageno = 0;
//		this.pagesize = 8;
//		this.sort = "id";
//	}
	public List<T> getDatas() {
		return datas;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(Integer totalpage) {
		this.totalpage = totalpage;
	}

	public Long getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(Long totalsize) {
		this.totalsize = totalsize;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public String getDescOrAsc() {
		return descOrAsc;
	}

	public void setDescOrAsc(String descOrAsc) {
		this.descOrAsc = descOrAsc;
	}

	public String getSortby() {
		return sortby;
	}

	public void setSortby(String sortby) {
		this.sortby = sortby;
	}

	public void setReault(Page<T> sqlResult) {
		setDatas(sqlResult.getContent());
		setTotalpage(sqlResult.getTotalPages());
		setTotalsize(sqlResult.getTotalElements());
		
	}

}
