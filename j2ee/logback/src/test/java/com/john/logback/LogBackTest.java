package com.john.logback;

import org.junit.Test;

import com.john.logback.a.ServiceA;
import com.john.logback.b.ServiceB;

public class LogBackTest {
	
	@Test
	public void test1() {
		ServiceA serviceA = new ServiceA();
		System.out.println("out print:" + serviceA);
		
		ServiceB serviceB = new ServiceB();
		System.out.println("out print:" + serviceB);
	}
}
