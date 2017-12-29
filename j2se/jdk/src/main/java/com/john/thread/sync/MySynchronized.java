package com.john.thread.sync;

public class MySynchronized {
	
	public synchronized void method1(long time) throws Exception{
		System.out.println("method1() is running by " + Thread.currentThread().getName());
		Thread.sleep(time);
		System.out.println("method1() is end by " + Thread.currentThread().getName());
	}
	
	public synchronized void method2(long time) throws Exception {
		System.out.println("method2() is running by " + Thread.currentThread().getName());
		Thread.sleep(time);
		System.out.println("method2() is end by " + Thread.currentThread().getName());
	}
	
	public synchronized void method3() throws Exception{
		System.out.println("method3() is running by " + Thread.currentThread().getName());
		method4();
		System.out.println("method3() is end by " + Thread.currentThread().getName());
	}
	
	private void method4() throws Exception {
		System.out.println("method4() is running by " + Thread.currentThread().getName());
		Thread.sleep(2000);
		System.out.println("method4() is end by " + Thread.currentThread().getName());
	}
}
