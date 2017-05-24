package com.john.test;

import org.junit.Test;

import com.john.MyBean;

public class TestSingle {
	@Test
	public void test1() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(MyBean.obtainBean().hashCode());
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(MyBean.obtainBean().hashCode());
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(MyBean.obtainBean().hashCode());
			}
		});
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}
	}
}
