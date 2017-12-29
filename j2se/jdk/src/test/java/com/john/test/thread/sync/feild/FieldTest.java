package com.john.test.thread.sync.feild;

import org.junit.Test;

/**
 * 这个是为了测试synchronized作用在不同的域上的效果
 * @author zhanghaocheng
 *
 */
public class FieldTest {
	@Test//经测试，两个synchronized方法的锁是会互斥的
	public void test1() throws InterruptedException {
		SyncObj syncObj = new SyncObj();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				syncObj.methodA();
			}
		});
		thread.start();
		Thread.sleep(500);//让他跟主线程稍微错开下时间
		syncObj.methodB();
	}
	
	@Test//经测试，一个synchronized方法跟其他非synchronized方法是不会互斥的
	public void test2() throws InterruptedException {
		SyncObj syncObj = new SyncObj();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				syncObj.methodA();
			}
		});
		thread.start();
		Thread.sleep(500);//让他跟主线程稍微错开下时间
		syncObj.methodC();
	}
	
	@Test//synchronized整个对象的话，其他方法的锁也不能使用
	public void test3() throws InterruptedException {
		SyncObj syncObj = new SyncObj();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				syncObj.methodD();
			}
		});
		thread.start();
		Thread.sleep(500);//让他跟主线程稍微错开下时间
		syncObj.methodA();
	}
	
	@Test//如果单一锁某一个变量的话，那么其他锁将不会受影响
	public void test4() throws InterruptedException {
		SyncObj syncObj = new SyncObj();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				syncObj.methodD();
			}
		});
		thread.start();
		Thread.sleep(500);//让他跟主线程稍微错开下时间
		syncObj.methodE();
	}
}
