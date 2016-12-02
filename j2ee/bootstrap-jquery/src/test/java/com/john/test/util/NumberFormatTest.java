package com.john.test.util;

import java.text.DecimalFormat;

import org.junit.Test;

public class NumberFormatTest {
	
	@Test
	public void test() {
		Float num = (1f / 3);
		System.out.println(num);
		
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数
		System.out.println(df.format(num));
	}
	
	@Test
	public void test1() {
		System.out.println((7/2));
	}
}
