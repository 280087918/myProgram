package com.john.test.thread.wait;

import org.junit.Test;

public class WaitTest {
	class MyWait {
		public synchronized void waitToWork() throws InterruptedException {
			System.out.println("马上进入等待状态");
			this.wait();
			System.out.println("不再等待");
		}
		
		public synchronized void notifyWork() {
			System.out.println("要解除等待了");
			this.notifyAll();
		}
	}
	
	@Test
	public void test() throws InterruptedException {
		MyWait myWait = new MyWait();
		Thread waitThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					myWait.waitToWork();
				} catch (InterruptedException e) {
				}
			}
		});
		
		Thread notifyThread = new Thread(new Runnable() {
			@Override
			public void run() {
				myWait.notifyWork();
			}
		});
		waitThread.start();
		Thread.sleep(2000);
		notifyThread.start();
		Thread.sleep(5000);//为了有个更好的观测效果
	}
}
