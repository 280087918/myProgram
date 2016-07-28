package com.john.Synchronized;

import org.junit.Test;

public class SynchronizedTest extends BaseTest {
	
	@Test
	public void test() {
		final Car car = new Car();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				car.add1();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				car.update1();
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
