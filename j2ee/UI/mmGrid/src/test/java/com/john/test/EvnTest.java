package com.john.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.service.CarService;
import com.john.utils.StringUtils;


/**
 * 环境测试类
 * @author zhang.hc
 * @date 2016年4月8日 上午9:32:50
 */
public class EvnTest extends BaseTestWithDB {
	@Autowired
	private CarService carService;
	
	@Test
	public void test1() {
		log.info("run....." + carService.listCars());
	}
	
	@Test
	public void test2() {
		log.info(StringUtils.uuid());
	}
}
