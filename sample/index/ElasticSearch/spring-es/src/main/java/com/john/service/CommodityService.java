package com.john.service;

import com.john.vo.Commodity;

public interface CommodityService extends BasicService<Commodity> {
	/**
	 * 根据id更新销量
	 * @param id
	 */
	public void updateBuyCountById(String id, Integer count);
}
