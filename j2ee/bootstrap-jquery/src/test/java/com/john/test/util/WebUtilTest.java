package com.john.test.util;

import org.junit.Test;

import com.john.system.utils.WebUtils;

public class WebUtilTest {
	@Test
	public void testUUID() {
		System.out.println(WebUtils.uuid());
	}
}
