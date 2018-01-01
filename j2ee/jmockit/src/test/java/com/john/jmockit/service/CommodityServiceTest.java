package com.john.jmockit.service;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;

import org.junit.Test;

import com.john.jmockit.entity.Commodity;
import com.john.jmockit.mapper.CommodityMapper;
import com.john.jmockit.mapper.StockMapper;
import com.john.jmockit.utils.CommodityUtil;

public class CommodityServiceTest {
	@Tested
	private MallService mallService = new MallServiceImpl();
	
	@Mocked
	@Injectable
	private StockMapper stockMapper;
	
	@Mocked
	@Injectable
	private CommodityMapper commodityMapper;
	
	@Test
	public void listCommodities() {
		//mock commodityMapper
		new Expectations() {{
			commodityMapper.selectCommodities();//需要替换的方法
			
			//组装结果
			List<Commodity> commodityList = new ArrayList<Commodity>();
			commodityList.add(new Commodity(1, "cup"));
			commodityList.add(new Commodity(2, "apple"));
			commodityList.add(new Commodity(3, "honey"));
			result = commodityList;//指定返回的结果
		}};
		
		//mock stockMapper
		new Expectations() {{
			stockMapper.selectStockByCommodiyId(1);result = 0;//cup have no stocks
			stockMapper.selectStockByCommodiyId(2);result = 20;
			stockMapper.selectStockByCommodiyId(3);result = 30;
		}};
		
		//mock static method
		new Expectations(CommodityUtil.class) {{
			CommodityUtil.assembleBrand(new Commodity(2, "apple"));result = "brand2";
			CommodityUtil.assembleBrand(new Commodity(3, "honey"));result = "brand3";
		}};
		
		//run the method
		List<Commodity> list = mallService.listCommodities();
		System.out.println(list);
	}
}
