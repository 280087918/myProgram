package com.john.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.ShopCart;

/**
 * 环境测试类
 * @author zhang.hc
 * @date 2016年4月8日 上午9:32:50
 */
public class EvnTest extends BaseTest {
	@Autowired
	ShopCart shopCart;
	
	@Test
	public void test() {
		log.info("shopCart:{}" + shopCart);
	}
}
