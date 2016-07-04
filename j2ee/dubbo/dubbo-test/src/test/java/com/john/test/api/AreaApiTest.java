package com.john.test.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.api.AreaApi;
import com.john.test.BasicTest;

public class AreaApiTest extends BasicTest {
	@Autowired
	private AreaApi areaApi;
	
	@Test
	public void test1() {
		log.info("::" + areaApi.listAreas());
	}
	
	@Test
	public void test2() {
		log.info("::" + areaApi.listAreas(3));
	}
}
