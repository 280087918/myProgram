package com.john.test.thread.join.order;

public class OrderObjA extends Thread {
	private OrderObjB orderObjB;
	private OrderObjC orderObjC;
	public OrderObjA(OrderObjB orderObjB, OrderObjC orderObjC) {
		this.orderObjB = orderObjB;
		this.orderObjC = orderObjC;
	}

	@Override
	public void run() {
		try {
			System.out.println("OrderObj A is running.");//这行无法保证在B和C之前执行。由CPU决定的
			
			sleep(2000);
			
			orderObjB.join();
			orderObjC.join();
			
			System.out.println("OrderObj A end.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//super.run();
	}
	
}
