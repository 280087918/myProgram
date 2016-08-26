package com.john.test.es;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.CommodityDao;
import com.john.test.BaseTest;
import com.john.vo.Commodity;

/**
 * 搜索品牌和类型
 * 搜索品牌能出来相应的类型
 * 搜索相应的类型能出来品牌
 * @author zhang.hc
 * @date 2016年8月25日 下午3:36:28
 */
public class CommodityTest extends BaseTest {
	@Autowired
	private CommodityDao commodityDao;
	
	@Test//批量测试数据插入
	public void saveBatch() {
		List<Commodity> commoditys = new ArrayList<Commodity>();
		commoditys.add(new Commodity("001", "格力空调1匹", "格力", "空调", 1000.0));
		commoditys.add(new Commodity("002", "格力空调2匹", "格力", "空调", 1000.0));
		commoditys.add(new Commodity("003", "美的空调1匹", "美的", "空调", 1000.0));
		commoditys.add(new Commodity("004", "美的空调2匹", "美的", "空调", 1000.0));
		commoditys.add(new Commodity("005", "格兰仕空调1匹", "格兰仕", "空调", 1000.0));
		commoditys.add(new Commodity("006", "格兰仕空调2匹", "格兰仕", "空调", 1000.0));
		
		commoditys.add(new Commodity("007", "捷安特电动车", "捷安特", "电动车", 1000.0));
		commoditys.add(new Commodity("008", "雅迪电动车", "雅迪", "电动车", 1000.0));
		commoditys.add(new Commodity("009", "爱玛电动车", "爱玛", "电动车", 1000.0));
		commoditys.add(new Commodity("010", "可爱小电驴", "爱玛", "电动车", 1000.0));
		
		commoditys.add(new Commodity("011", "美的热水器", "美的", "热水器", 1000.0));
		commoditys.add(new Commodity("012", "海尔热水器", "海尔", "热水器", 1000.0));
		
		commodityDao.batchSaveCommodity(commoditys);
	}
	
	@Test
	public void testSearchList() {
		List<Commodity> commoditys = commodityDao.searchList("爱玛");
		if(CollectionUtils.isNotEmpty(commoditys)) {
			for(Commodity commodity : commoditys) {
				log.info("" + commodity);
			}
		} else {
			logger.info("空的......");
		}
	}
}
