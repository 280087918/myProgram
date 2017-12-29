package com.john.thread.util.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发包里面的AtomicInteger
 *  距资料介绍说是原子性的
 * @author zhanghaocheng
 *
 */
public class AtomicIntegerObj {
	private AtomicInteger count = null;
	
	public AtomicIntegerObj(){super();}
	public AtomicIntegerObj(int outCount) {
		count =  new AtomicInteger(outCount);
	}
	
	//传负数过来
	public int decr(int dec) {
		return count.addAndGet(dec);
	}
}
