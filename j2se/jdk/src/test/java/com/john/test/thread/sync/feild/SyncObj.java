package com.john.test.thread.sync.feild;

public class SyncObj {
	private Object lock = new Object();
	
	public synchronized void methodA() {
		try {
			System.out.println("methodA is running.");
			Thread.sleep(3000);
			System.out.println("methodA is end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void methodB() {
		try {
			System.out.println("methodB is running.");
			Thread.sleep(3000);
			System.out.println("methodB is end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void methodC() {
		try {
			System.out.println("methodC is running.");
			Thread.sleep(3000);
			System.out.println("methodC is end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void methodD() {
		try {
			synchronized (this) {
				System.out.println("methodD is running.");
				Thread.sleep(3000);
				System.out.println("methodD is end.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void methodE() {
		try {
			synchronized (lock) {
				System.out.println("methodE is running.");
				Thread.sleep(3000);
				System.out.println("methodE is end.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
