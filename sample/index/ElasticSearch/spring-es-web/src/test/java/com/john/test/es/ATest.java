package com.john.test.es;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.AService;
import com.john.test.BaseTest;
import com.john.vo.A;
import com.john.vo.B;

public class ATest extends BaseTest {
	@Autowired
	private AService aService;
	
	@Test
	public void testSave() {
		A a = new A("001", "a1");
		a.addB(new B("001", "b1"));
		a.addB(new B("002", "b2"));
		aService.saveA(a);
	}
	
	@Test
	public void testFind1() {
		A a = aService.findAById("001");
		log.info("a:{}", a);
	}
	
	@Test
	public void testFind2() {
		A a = aService.findASimiJoin("002");
		log.info("a:{}", a);
	}
}
