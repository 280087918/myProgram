package com.john.api;

import java.util.List;

import com.john.dto.AreaDto;

public interface AreaApi {
	/**
	 * 列举所有的区域
	 * @return
	 */
	public List<AreaDto> listAreas();
	
	public List<AreaDto> listAreas(Integer i);
}
