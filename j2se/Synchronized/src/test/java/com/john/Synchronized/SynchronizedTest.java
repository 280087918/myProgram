package com.john.Synchronized;

public class SynchronizedTest extends BaseTest {
	
	public static void main1(String[] args) {
		final User user = new User();
		
		log.info("---------------test begin---------------");
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				user.add();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				user.update();
			}
		});
		
		t1.start();
		t2.start();
		log.info("---------------test end---------------");
	}
	
	public static void main(String[] args) {
		final Car car = new Car();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				car.add();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				car.update();
			}
		});
		
		t1.start();
		t2.start();
	}
}
