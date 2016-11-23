package com.john.service;

import java.util.List;
import java.util.Map;

import com.john.dto.Commodity;

public interface CommodityService extends BasicService<Commodity> {
	/**
	 * 搜索三板斧
	 * @param params
	 * @return
	 */
	public List<Commodity> searchList(Map<String, Object> params);
}
