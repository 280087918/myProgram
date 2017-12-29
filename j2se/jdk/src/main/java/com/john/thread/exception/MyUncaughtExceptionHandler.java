package com.john.thread.exception;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 异常处理类
 * @author zhanghaocheng
 *
 */
public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("caught " + e);
	}

}
