package com.john.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.Commodity;

public interface CommodityDao {
	Logger log = LoggerFactory.getLogger(CommodityDao.class);
	
	public void saveCommodity(Commodity commodity);
	
	public void batchSaveCommodity(List<Commodity> commoditys);
	
	public List<Commodity> searchList(String keyword);
}
