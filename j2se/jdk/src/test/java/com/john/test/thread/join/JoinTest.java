package com.john.test.thread.join;

import org.junit.Test;

import com.john.thread.join.Joiner;
import com.john.thread.join.Sleeper;

public class JoinTest {
	/**
	 * join可以将当前线程加入到目标线程当中，待目标线程结束后才会开启本线程
	 * 	join方法可以用interrupt()方法进行中断.
	 */
	@SuppressWarnings("unused")
	@Test
	public void test1() {
		Sleeper sleepy = new Sleeper("Sleepy", 1500);
		Sleeper grumy = new Sleeper("grumy", 1500);
		
		Joiner dopey = new Joiner("Dopey", sleepy);
		Joiner doc = new Joiner("doc", grumy);
		
		grumy.interrupt();
		
		try {
			//Thread.sleep(2000);
			dopey.join();//本线程加入到目标线程中，挂起，直至目标线程结束之后再次运行本线程
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
