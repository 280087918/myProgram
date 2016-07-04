package com.john.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.api.AreaApi;
import com.john.dto.AreaDto;

public class AreaApiImpl implements AreaApi {
	Logger log = LoggerFactory.getLogger(AreaApiImpl.class);

	@Override
	public List<AreaDto> listAreas() {
		log.info("dubbo rpc调用【AreaApi.listAreas】");
		//正常情况应该注入service做业务操作的，但是这里就省略了这个步骤
		List<AreaDto> areas = new ArrayList<AreaDto>();
		areas.add(new AreaDto("001", "广州", "gz"));
		areas.add(new AreaDto("002", "西安", "xa"));
		areas.add(new AreaDto("003", "咸阳", "xy"));
		areas.add(new AreaDto("004", "北京", "bj"));
		areas.add(new AreaDto("005", "天津", "tj"));
		areas.add(new AreaDto("006", "深圳", "sz"));
		areas.add(new AreaDto("007", "香港", "hk"));
		return areas;
	}

	@Override
	public List<AreaDto> listAreas(Integer i) {
		//正常情况应该注入service做业务操作的，但是这里就省略了这个步骤
		List<AreaDto> areas = new ArrayList<AreaDto>();
		areas.add(new AreaDto("001", "广州", "gz"));
		areas.add(new AreaDto("002", "西安", "xa"));
		areas.add(new AreaDto("003", "咸阳", "xy"));
		areas.add(new AreaDto("004", "北京", "bj"));
		areas.add(new AreaDto("005", "天津", "tj"));
		areas.add(new AreaDto("006", "深圳", "sz"));
		areas.add(new AreaDto("007", "香港", "hk"));
		log.info("listAreas(2):{}", i);
		return areas;
	}
}
