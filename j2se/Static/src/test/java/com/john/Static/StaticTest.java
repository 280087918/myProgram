package com.john.Static;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticTest {
	private final static Logger log = LoggerFactory.getLogger(StaticTest.class);
	
	@Test
	public void test1() {
		//{}称之为构造块，每次创建对象的时候都会执行一次
		BankAccountBlock b1 = new BankAccountBlock();
		BankAccountBlock b2 = new BankAccountBlock();
		
		//static{}静态快，无论创建多少个对象只会执行一次
		BankAccountStatic s1 = new BankAccountStatic();
		BankAccountStatic s2 = new BankAccountStatic();
	}
}
