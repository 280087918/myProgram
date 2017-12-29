package com.john.test.thread.join.order;

public class OrderObjB extends Thread {

	@Override
	public void run() {
		System.out.println("OrderObj B is running.");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("OrderObj B end.");
		//super.run();
	}
	
}
