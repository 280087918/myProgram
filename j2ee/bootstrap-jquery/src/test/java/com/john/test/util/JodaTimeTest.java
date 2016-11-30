package com.john.test.util;

import org.joda.time.DateTime;
import org.junit.Test;

public class JodaTimeTest {
	@Test//当天的零点
	public void testCurrentDay1() {
		DateTime dt=new DateTime().withMillisOfDay(0);
		System.out.println(dt.toString("yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateTime.now().withMillisOfDay(0));
	}
	
	@Test
	public void testExactDate() {
		DateTime dt = new DateTime("2016-12-01T00:00:00");
		System.out.println(dt.toString("yyyy-MM-dd HH:mm:ss"));
		
		DateTime dt2 = dt.plusHours(2);
		System.out.println(dt2.toString("yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void testHour() {
		System.out.println(DateTime.now().getHourOfDay());
	}
}
