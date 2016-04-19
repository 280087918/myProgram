package com.john.base.vo;

import java.util.List;

/**
 * 分页Vo
 * @author zhang.hc
 * @date 2016年4月18日 下午4:15:53
 */
public class PageVo<T> {
	//当前页码
	private int pageIndex = 1;
	
	//分页大小
	private int pageSize = 10;
	
	//这里不考虑多个字段进行排序的情况
	private String orderBy;
	
	private List<T> data;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int firstIndex() {
		return (pageIndex - 1) * pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
