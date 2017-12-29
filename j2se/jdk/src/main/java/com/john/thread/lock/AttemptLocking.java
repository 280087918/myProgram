package com.john.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
	public ReentrantLock lock = new ReentrantLock();
	public void untimed() {
		boolean captured = lock.tryLock();
		try {
			System.out.println("tryLock():" + captured);
		} finally {
			if(captured)
				lock.unlock();
		}
	}
	
	public void timed() {
		boolean captured = false;
		try {
			captured = lock.tryLock(2, TimeUnit.SECONDS);
			System.out.println("tryLock(2, TimeUnit.SECONDS):" + captured);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(captured)
				lock.unlock();
		}
	}
}
