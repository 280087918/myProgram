package com.john.test.thread.daemon;

import org.junit.Test;

public class DaemonTest {
	@Test//守护线程，不过这个没按照我的想法走，还不知道为什么
	public void test() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("thread will sleep ......");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread end ......");
			}
		});
		
		thread.setDaemon(false);
		thread.start();
	}
	
	@Test
	public void test2() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("thread will sleep ......");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread end ......");
			}
		});
		
		thread.join();
		thread.start();
	}
}
