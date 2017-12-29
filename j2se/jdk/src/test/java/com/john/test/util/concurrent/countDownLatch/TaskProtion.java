package com.john.test.util.concurrent.countDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TaskProtion implements Runnable {
	private static int counter = 0;
	private final int id = counter ++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;
	
	TaskProtion(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			doWork();
			latch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		System.out.println(this + " complete");
	}

	@Override
	public String toString() {
		return String.format("%1$-3d", id);
	}
	
}
