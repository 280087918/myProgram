package com.john.test.es;

import org.joda.time.DateTime;
import org.junit.Test;

public class MMTEst {
	@Test
	public void test() {
		DateTime dt1 = new DateTime(1479859200000l);
		System.out.println(dt1);
		
		DateTime dt2 = new DateTime(1480035600000l);
		System.out.println(dt2);
	}
}
