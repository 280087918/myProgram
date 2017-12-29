package com.john.test.util.concurrent.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.john.thread.util.atomic.AtomicIntegerObj;
import com.john.thread.util.atomic.NormalIntegerObj;

public class AtomicTest {
	
	@Test//这个经过测试可以实现高并发状况。线程安全
	public void testAtomic() throws InterruptedException {
		AtomicIntegerObj obj = new AtomicIntegerObj(10000);//给一万个stock
		
		//不想并发太多看不清,主要是想看看后面能不能扣到0,跟抢购一样
		ExecutorService execs = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 5000; i++) {
			execs.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(obj.decr(-2));
				}
			});
		}
		execs.shutdown();
		Thread.sleep(8000);//睡上个8s好观测
	}
	
	@Test//经过测试这个并发到后面并不能扣除到0，最终的输出数字为2
	public void testNormal() throws InterruptedException {
		NormalIntegerObj obj = new NormalIntegerObj(10000);
		//不想并发太多看不清,主要是想看看后面能不能扣到0,跟抢购一样
		ExecutorService execs = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 5000; i++) {
			execs.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(obj.decr(-2));
				}
			});
		}
		execs.shutdown();
		Thread.sleep(8000);//睡上个8s好观测
	}
}
