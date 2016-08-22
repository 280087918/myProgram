package com.john.vo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class MyPageable implements Pageable {
	
	private int pageNumber = 0;
	
	private int pageSize = 1;
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}
	
}
