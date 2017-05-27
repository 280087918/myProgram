package com.john.concurrent;

import org.junit.Test;

import com.john.vo.ConcurrentObj;

public class ConcurrentTest {
	@Test
	public void test1() throws InterruptedException {
		ConcurrentObj obj = new ConcurrentObj(100);
		MyThread t = new MyThread(obj);
		t.start();
		
		Thread.sleep(2000);
		
		obj.setCount(200);
		MyThread t1 = new MyThread(obj);
		t1.start();
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	class MyThread extends Thread {
		private ConcurrentObj obj = null;
		
		public MyThread() {super();}
		public MyThread(ConcurrentObj obj) {
			this.obj = obj;
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					Thread.sleep(1000);
					System.out.println("tn:" + currentThread().getName() + ", count:" + obj.getCount());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
