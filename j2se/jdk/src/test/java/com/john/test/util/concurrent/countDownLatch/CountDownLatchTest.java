package com.john.test.util.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class CountDownLatchTest {
	static final int SIZE = 100;
	
	@Test
	public void test() throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(SIZE);
		for (int i = 0; i < 10; i++)
			exec.execute(new WaitingTask(latch));
		for (int i = 0; i < SIZE; i++)
			exec.execute(new TaskProtion(latch));
		System.out.println("Launch all tasks");
		Thread.sleep(10000);
		exec.shutdown();
	}
}
