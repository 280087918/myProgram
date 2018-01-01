package com.john.jmockit.service;

import java.util.ArrayList;
import java.util.List;

import com.john.jmockit.entity.Commodity;
import com.john.jmockit.mapper.CommodityMapper;
import com.john.jmockit.mapper.StockMapper;
import com.john.jmockit.utils.CommodityUtil;

public class MallServiceImpl implements MallService {
	private StockMapper stockMapper;
	private CommodityMapper commodityMapper;

	public List<Commodity> listCommodities() {
		List<Commodity> commodityList = new ArrayList<Commodity>();
		List<Commodity> commodities = commodityMapper.selectCommodities();
		commodities.forEach(commodity -> {
			Integer stock = stockMapper.selectStockByCommodiyId(commodity.getId());
			if(stock > 0) {
				commodity.setBrand(CommodityUtil.assembleBrand(commodity));
				commodityList.add(commodity);
			}
		});
		return commodityList;
	}

}
