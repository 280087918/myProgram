package com.john.test.es;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.CService;
import com.john.test.BaseTest;
import com.john.vo.C;

public class CTest extends BaseTest {
	@Autowired
	private CService cService;
	
	@Test
	public void testSave() {
		cService.saveC(new C("001", "第一个让你啥都有", 10));
		cService.saveC(new C("002", null, 10));
		cService.saveC(new C("003", "ha", null));
		cService.saveC(new C("004", null, null));
		cService.saveC(new C("005", null, null));
	}
	
	@Test
	public void testFind() {
		C c = cService.findCById("002");
		System.out.println(c);
	}
}
