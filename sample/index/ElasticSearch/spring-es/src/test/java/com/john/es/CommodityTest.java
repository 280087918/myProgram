package com.john.es;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.service.CommodityService;
import com.john.test.BaseTest;
import com.john.vo.Commodity;

public class CommodityTest extends BaseTest {
	@Autowired
	CommodityService commodityService;
	
	@Test
	public void preData() {
		save();//不想看到那一堆方法
	}
	
	@Test
	public void clearData() {
		commodityService.clearData(Commodity.class);
	}
	
	private void save() {
		commodityService.persistObj(new Commodity("c001", "格力空调冷静王-Ⅱ KFR-35G(35583)FNAa-A3", 6), "c001");
		commodityService.persistObj(new Commodity("c002", "至尊呵护棉压花夏凉被&mdash", 2), "c002");
		commodityService.persistObj(new Commodity("c003", "伊努维克空调KFR-35G/DY-820CA挂式机", 3), "c003");
		commodityService.persistObj(new Commodity("c004", "美的空调KFR-32GW/DY-DA400(D3)", 4), "c004");
		commodityService.persistObj(new Commodity("c005", "TCL空调KFRD-35G/LC33BpA", 5), "c005");
		commodityService.persistObj(new Commodity("c006", "智能收音机S22", 6), "c006");
	}
}
