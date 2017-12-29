package com.john.test.thread.interupt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 对于正在运行的线程进行中断
 * @author zhanghaocheng
 *
 */
public class InteruptTest {
	//----------------睡眠是可以被中断的
	class SleepBlocked implements Runnable {
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(100);//睡上100秒
			} catch (InterruptedException e) {
				//可以执行到这里
				System.out.println("引发InterruptedException");
			}
		}
	}
	
	@Test
	public void testSleep() throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		Future<?> future = exec.submit(new SleepBlocked());
		TimeUnit.MILLISECONDS.sleep(100);//睡上100毫秒，让结果更加明显
		System.out.println("正在中断sleep");
		future.cancel(true);
//		exec.shutdownNow();//这个也可以中断线程
		System.out.println("中断已经发送至sleep");
	}
	
	//----------------睡眠是可以被中断的
}
