package com.john.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.CommodityBrandType;

public interface CommodityBrandTypeDao {
	Logger log = LoggerFactory.getLogger(CommodityBrandTypeDao.class);
	
	public void saveBrandType(CommodityBrandType brandType);
	
	public List<CommodityBrandType> searchListByBrandAndType(String brand, String type);
}
