package com.john.test.beanpostprocessor;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.beanpostprocessor.MyServiceImpl;
import com.john.test.BaseTest;

/**
 * 控制bean在初始化以及结束时该做的事情
 * @author zhang.hc
 * @date 2016年6月25日 上午11:39:41
 */
public class BeanPostProcessorTest extends BaseTest {
	@Autowired
	MyServiceImpl myService;
	
	@Test
	public void test1() {
		log.info("gaga");
	}
	
	@Test
	public void testSave() {
		myService.save();
	}
	
	@Test
	public void testQuery() {
		myService.query();
	}
}
