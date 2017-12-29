package com.john.thread.exception;

public class ExceptionThread implements Runnable {

	@Override
	public void run() {
		Thread t = Thread.currentThread();
		//这里不输出都无所谓，只是为了证明这个线程已经设置了一个异常处理类
		System.out.println("UncaughtHandler:" + t.getUncaughtExceptionHandler());
		throw new RuntimeException();
	}
}
