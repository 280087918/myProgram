package com.john.test.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.john.thread.runnable.LiftOff;

public class ExecutorTest {
	@Test
	public void testCachedThreadPool() {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new LiftOff());
		}
		exec.shutdown();
	}
	
	@Test
	public void testFixThreadPool() throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 5; i++) {
			exec.execute(new LiftOff());
		}
		exec.shutdown();
		Thread.sleep(500);
	}
}
