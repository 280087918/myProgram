package com.john.test.commodity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;

import com.john.dto.Commodity;
import com.john.service.CommodityService;
import com.john.test.BaseTest;

public class CommodityTest extends BaseTest {
	@Autowired
	CommodityService commodityService;
	
	@Test//插入单条商品数据
	public void saveCommodity() {
		Commodity commodity = new Commodity("034bd8cba5d94694a8763e8b5de45eee", "掌上明珠 电视TG2156-X  (2035-2400)mm*350mm*415mm", 15);
		commodityService.persistObj(commodity, commodity.getGoodsId());
		
		commodity = new Commodity("99f81153bf5c434e9e254506f815317a", "足球、篮球、皮马、游泳圈、自行车等多功能小气筒", 15);
		commodityService.persistObj(commodity, commodity.getGoodsId());
		
		commodity = new Commodity("9a3d441461c64d099e1c8254aeb982cc", "秦歌多功能唱戏机QG-632 红色(附送内存卡一张)", 10);
		commodityService.persistObj(commodity, commodity.getGoodsId());
		
		commodity = new Commodity("df631bc683cd46c7b3dbb658098f48c7", "在线少女生理内裤 浅紫 均码 5216", 13);
		commodityService.persistObj(commodity, commodity.getGoodsId());
	}
	
	@Test//删除索引
	public void removeCommodityIndex() {
		commodityService.removeIndex(Commodity.class.getAnnotation(Document.class).indexName());
	}
	
	@Test//根据id查询单个商品对象
	public void findCommodity() {
		Commodity commodity = commodityService.findObjectById(Commodity.class, "goodsId", "034bd8cba5d94694a8763e8b5de45eee");
		logger.info("commodity:{}", commodity);
	}
	
	@Test//根据单个条件查询一个或多个商品
	public void findCommoditys() {
		List<Commodity> list = commodityService.findObjects(Commodity.class, "buyCount", 15);
		logger.info("list:{}", list);
	}
	
	@Test//根据一个term查询分词器的域
	public void matchCommoditys() {
		List<Commodity> list = commodityService.matchObjects(Commodity.class, "name", "多功能");
		logger.info("list:{}", list);
	}
	
	/**
	 * `ik_smart` , `ik_max_word`
	 * http://192.168.22.181:9200/_analyze?analyzer=ik_smart&text=足球、篮球、皮马、游泳圈、自行车等多功能小气筒
	 */
	@Test//根据条件搜索集合，目前主要是调优搜索特性
	public void searchCommodity() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keywords", "均瑶 味动力");
		List<Commodity> commoditys = commodityService.searchList(params);
		if(CollectionUtils.isNotEmpty(commoditys)) {
			for(Commodity commodity : commoditys) {
				logger.info("{}", commodity);
			}
		} else {
			logger.warn("没有找到相关信息");
		}
	}
}
