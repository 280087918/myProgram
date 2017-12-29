package com.john.test.thread.sync;

import org.junit.Test;

import com.john.thread.sync.MySynchronized;

public class MySynchronizedTest {
	/**
	 * 同一个对象里面存在多个synchronized方法，那么两个锁之间也会互斥。
	 * 	所以能看到线程1的method1方法执行完后，才轮到线程2执行method2
	 * 	这个曾经是"非凡之星"在支付的逻辑上遇到的问题。
	 */
	@Test
	public void test1() {
		MySynchronized mySync = new MySynchronized();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.currentThread().setName("thread1");
					mySync.method1(1000);
					mySync.method2(2000);
				} catch (Exception e) {
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.currentThread().setName("thread2");
					mySync.method2(0);
					mySync.method1(0);
				} catch (Exception e) {
				}
			}
		}).start();
		try {
			Thread.sleep(8000);
		} catch (Exception e) {
		}
	}
	
	/**
	 * 锁会递归到下面的方法里面，
	 * 	虽然synchronized方法执行完了，但是调用的方法还在之星，虽然调用的方法没有synchronized，不过依然会被锁定
	 */
	@Test
	public void test2() {
		MySynchronized mySync = new MySynchronized();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.currentThread().setName("thread1");
					mySync.method3();
				} catch (Exception e) {
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.currentThread().setName("thread2");
					mySync.method3();
				} catch (Exception e) {
				}
			}
		}).start();
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	}
}
