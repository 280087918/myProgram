package com.john.test.jedis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.service.MyRedisService;
import com.john.test.BaseTest;

public class LockTest extends BaseTest {
	@Autowired
	MyRedisService myRedisService;
	
	@Test
	public void test() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				myRedisService.processMyProcess();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				myRedisService.processMyProcess();
			}
		});
		
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				myRedisService.processMyProcess();
			}
		});
		
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				myRedisService.processMyProcess();
			}
		});
		
		Thread t5 = new Thread(new Runnable() {
			@Override
			public void run() {
				myRedisService.processMyProcess();
			}
		});
		
		System.out.println("=====================测试开始========================");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("=====================测试结束========================");
	}
}
