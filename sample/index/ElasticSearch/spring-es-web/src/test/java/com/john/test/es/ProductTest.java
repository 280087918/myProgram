package com.john.test.es;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.ProductDao;
import com.john.test.BaseTest;
import com.john.vo.Product;

public class ProductTest extends BaseTest {
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testTips() {
		String keyword = "指纹";
		List<String> tips = productDao.searchTips(keyword);
		log.info("搜索提示:" + tips);
	}
	
	@Test
	public void testList() {
		List<Product> list = productDao.searchList("闪充");
		log.info("结果:" + list);
	}
	
	@Test
	public void saveProducts() {
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("001", "小米 小米手机5 全网通3.0， 新一代快充3.0 技术， 16颗灯省电高亮屏", "小米", 2601.00));
		products.add(new Product("002", "华为 P9 徕卡双镜头， 3D指纹信息识别", "华为", 2988.00));
		products.add(new Product("003", "三星 Galaxy A9 高配版 5000毫安时大电池， 急速充电", "三星", 2988.00));
		products.add(new Product("004", "vivo X6S Plus 急速指纹，畅快识别， 双引擎闪充，畅快充电", "vivo", 2988.00));
		products.add(new Product("005", "vivo Xplay5 双曲面屏幕，视觉无边界， 一体成型金属机身， 双载波聚", "vivo", 3627.00));
		products.add(new Product("006", "vivo X6S 4G大运存，够快才畅快， 两倍充电速度，九重安全防护", "vivo", 2598.00));
		products.add(new Product("007", "OPPO R9 正面指纹识别， VOOC闪充", "OPPO", 2799.00));
		products.add(new Product("008", "乐视 乐2 乐镜指纹，速度超群， 新一代乐闪冲，双向正反插设计", "乐视", 1229.00));
		products.add(new Product("009", "OPPO R9 Plus 搭配VOOC闪充， 充电5分钟，通话2小时", "OPPO", 3200.00));
		products.add(new Product("010", "乐视 乐Max2 超声波金属指纹识别， 正反插Type-C接口", "乐视", 2266.00));
		products.add(new Product("011", "三星 Galaxy S7 edge 防尘防水， 侧屏快捷应用， 快速充电模块", "三星", 5688.00));
		products.add(new Product("012", "三星 Galaxy S7 IP68级三防技术， 按压式指纹识别， 快速充电无线充电", "三星", 4468.00));
		products.add(new Product("013", "vivo V3Max A 急速指纹， 急速闪充， 分屏多任务", "三星", 2098.00));
		products.add(new Product("014", "魅族 PRO 6 10核定制处理器， mTouch 2.1指纹识别", "魅族", 2499.00));
		products.add(new Product("015", "苹果 iPhone SE 4K视频拍摄， 指纹识别， 支持Apple Pay", "苹果", 3236.00));
		products.add(new Product("016", "乐视 乐2 Pro 新一代乐闪冲，双向正反插设计， 无边框3.0", "乐视", 1363.00));
		
		productDao.batchSaveProduct(products);
	}
}
