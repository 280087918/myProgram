package com.john.test.utils;

import org.joda.time.DateTime;
import org.junit.Test;

public class JodaTimeTest {
	@Test
	public void longTime() {
		DateTime beginDateTime = new DateTime(1481521364000l);
		DateTime endDateTime = new DateTime(1481780565000l);
		System.out.println(beginDateTime);
		System.out.println(endDateTime);
	}
}
