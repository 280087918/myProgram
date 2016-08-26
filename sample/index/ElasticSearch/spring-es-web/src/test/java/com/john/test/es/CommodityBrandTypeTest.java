package com.john.test.es;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.CommodityBrandTypeDao;
import com.john.test.BaseTest;
import com.john.vo.CommodityBrandType;

public class CommodityBrandTypeTest extends BaseTest {
	@Autowired
	private CommodityBrandTypeDao commodityBrandTypeDao;
	
	@Test
	public void saveOne() {
		commodityBrandTypeDao.saveBrandType(new CommodityBrandType("001", "爱玛", "电动车"));
		commodityBrandTypeDao.saveBrandType(new CommodityBrandType("002", "爱玛电动车", "电动车"));
	}
	
	@Test
	public void findExactlyOne() {
		List<CommodityBrandType> commodityBrandTypes = commodityBrandTypeDao.searchListByBrandAndType("爱玛", "电动车");
		log.info("" + commodityBrandTypes);
	}
}
