package com.john.api.impl;

import java.util.ArrayList;
import java.util.List;

import com.john.api.AreaApi;
import com.john.dto.AreaDto;

public class AreaApiImpl implements AreaApi {
	static List<AreaDto> areas = null;
	static {
		areas = new ArrayList<AreaDto>();
		areas.add(new AreaDto("001", "广州", "gz"));
		areas.add(new AreaDto("002", "西安", "xa"));
		areas.add(new AreaDto("003", "咸阳", "xy"));
		areas.add(new AreaDto("004", "北京", "bj"));
		areas.add(new AreaDto("005", "天津", "tj"));
		areas.add(new AreaDto("006", "深圳", "sz"));
		areas.add(new AreaDto("007", "香港", "hk"));
	}

	@Override
	public List<AreaDto> listAreas(Integer i) {
		List<AreaDto> returnList = null;
		if(i > areas.size()) {
			returnList = areas;
		} else {
			returnList = areas.subList(0, i);
		}
		System.out.println("i:" + i + ", " + returnList);
		return returnList;
	}

}
