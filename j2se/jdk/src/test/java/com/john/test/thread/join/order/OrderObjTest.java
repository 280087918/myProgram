package com.john.test.thread.join.order;

import org.junit.Test;

public class OrderObjTest {
	@Test
	public void test1() throws InterruptedException {
		OrderObjB orderObjB = new OrderObjB();
		OrderObjC orderObjC = new OrderObjC();
		OrderObjA orderObjA = new OrderObjA(orderObjB, orderObjC);
		
		orderObjB.start();
		orderObjC.start();
		orderObjA.start();
		
		Thread.sleep(10000);
	}
}
