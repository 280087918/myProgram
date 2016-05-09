package com.john.test.env;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.TestEntity;
import com.john.test.BaseTest;

public class ATest extends BaseTest {
	@Autowired
	TestEntity testEntity;
	
	@Test
	public void test() {
		log.info("" + testEntity.toString());
	}
}
