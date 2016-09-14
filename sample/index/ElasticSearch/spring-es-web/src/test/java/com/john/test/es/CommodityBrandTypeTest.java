package com.john.test.es;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.CommodityBrandTypeDao;
import com.john.test.BaseTest;
import com.john.vo.CommodityBrandType;

/**
 * 若干种搜索的方式
 * @author zhang.hc
 * @date 2016年8月29日 下午2:38:10
 */
public class CommodityBrandTypeTest extends BaseTest {
	@Autowired
	private CommodityBrandTypeDao commodityBrandTypeDao;
	
	@Test
	public void saveOne() {
		commodityBrandTypeDao.saveBrandType(new CommodityBrandType("001", "爱玛", "电动车"));
		commodityBrandTypeDao.saveBrandType(new CommodityBrandType("002", "爱玛电动车", "电动车"));
	}
	
	/**
	 * 使用Spring-data的Criteria
	 * 不过好像没达到我要的效果，我需要完整匹配的，这里不支持
	 */
	@Test
	public void findExactlyOne() {
		List<CommodityBrandType> commodityBrandTypes = commodityBrandTypeDao.searchCriteria("爱玛", "电动车");
		log.info("" + commodityBrandTypes);
	}
	
	
}
