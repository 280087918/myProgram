package com.john.test.java;

import org.junit.Test;

/**
 * 配合分布式锁测试的
 * @author zhang.hc
 * @date 2016年11月9日 下午4:14:46
 */
public class JavaTest {
	
	@Test//证明了System.currentTimeMillis()不是跟我想的一样，我原以为是系统启动初期是多少就一致多少。但是这个原来还真的是实时的.
	public void test() {
		int timeout = 1000;
		while(timeout > 0) {
			System.out.println(System.currentTimeMillis());
			timeout -= 100;
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}
}
