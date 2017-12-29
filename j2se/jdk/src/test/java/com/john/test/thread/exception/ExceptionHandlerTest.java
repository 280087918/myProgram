package com.john.test.thread.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.john.thread.exception.ExceptionThread;
import com.john.thread.exception.HandlerThreadFactory;
import com.john.thread.exception.MyUncaughtExceptionHandler;

public class ExceptionHandlerTest {
	/**
	 * 这样异常就会在异常处理类那里被捕捉到
	 */
	@Test
	public void test1() {
		try {
			ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
			exec.execute(new ExceptionThread());
		} catch (Exception e) {
			System.out.println("还是抓不住的");
		}
	}
	
	/**
	 * 这种效果也是可以的，只不过域不一样。这样设置是程序中所有的异常都交由这个异常处理类来处理
	 */
	@Test
	public void test2() {
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new ExceptionThread());
	}
}
