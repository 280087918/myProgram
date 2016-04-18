package com.john.test.other;

import org.junit.Test;
import org.springframework.util.Assert;

public class AssertTest {
	@Test
	public void test1() {
		String a = "a";
		Assert.hasText(a, "????");
	}
}
