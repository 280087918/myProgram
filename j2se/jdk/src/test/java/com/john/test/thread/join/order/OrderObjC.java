package com.john.test.thread.join.order;

public class OrderObjC extends Thread {

	@Override
	public void run() {
		System.out.println("OrderObj C is running.");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("OrderObj C end.");
		//super.run();
	}
	
}
