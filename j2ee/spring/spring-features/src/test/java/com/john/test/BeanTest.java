package com.john.test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.beanpostprocessor.MyServiceImpl;
public class BeanTest extends BaseTest {
	@Autowired
	MyServiceImpl myService;
	
	@Test
	public void test() {
		myService.save();
	}
}
