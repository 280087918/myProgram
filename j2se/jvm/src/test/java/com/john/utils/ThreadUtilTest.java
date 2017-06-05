package com.john.utils;

import org.junit.Test;

public class ThreadUtilTest {
	private int stock = 10000;
	public void descStock(Integer desc) {
		stock  = stock - desc;
		System.out.println(Thread.currentThread().getName() + ", stock now is:" + stock);
	}
	
	public void print() {
		System.out.println(Thread.currentThread().getName());
	}
	
	@Test
	public void run() throws Exception {
		Thread[] threads = ThreadsUtils.obtainThreads(this, "descStock", 5000, 2);
		ThreadsUtils.runThreads(threads);
		Thread.sleep(Long.MAX_VALUE);
	}
	
	@Test
	public void run2() throws Exception {
		Thread[] threads = ThreadsUtils.obtainThreads(this, "print", 5000);
		ThreadsUtils.runThreads(threads);
		Thread.sleep(Long.MAX_VALUE);
	}
}
