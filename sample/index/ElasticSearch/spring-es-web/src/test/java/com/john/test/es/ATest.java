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
		
		a.addS("a_s_1");
		a.addS("a_s_2");
		a.addS("a_s_3");
		aService.saveA(a);
		
		A a1 = new A();
		a1.addB(new B("001", "b1"));
		a1.addB(new B("002", "b2"));
		
		a1.addS("a_s_4");
		a1.addS("a_s_5");
		a1.addS("a_s_6");
		aService.saveA(a1);
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
	
	@Test
	public void testFind3() {//阔以
		A a = aService.findAsimiJoinS("a_s_1");
		log.info("a:{}", a);
	}
}
