package com.john.test.thread.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * 这个例子只是为了说明一般情况下线程的异常是不会被上层捕捉到的
 * @author zhanghaocheng
 *
 */
public class ExceptionTest {
	@Test
	public void test1() {
		try {
			ExecutorService exec = Executors.newCachedThreadPool();
			exec.execute(new ExceptionThread());
		} catch (Exception e) {
			//这里是不会输出的，捕获不到线程里面的异常
			System.out.println("exception caught......");
		}
	}
	
	class ExceptionThread implements Runnable {
		@Override
		public void run() {
			System.out.println("thread running......");
			throw new RuntimeException();
		}
	}
}
