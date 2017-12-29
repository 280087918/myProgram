package com.john.test.thread.lock;

import org.junit.Test;

import com.john.thread.lock.AttemptLocking;

public class AttemptLockingTest {
	@Test
	public void test1() {
		AttemptLocking al = new AttemptLocking();
		al.untimed();
		al.timed();
		new Thread() {
			{setDaemon(true);}
			public void run() {
				al.lock.lock();
				System.out.println("acquired");
			}
		}.start();
		Thread.yield();
		al.untimed();
		al.timed();
	}
}
