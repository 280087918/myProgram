package com.john.vo;

/**
 * 用来测试并发的
 * @author Administrator
 *
 */
public class ConcurrentObj {
	private int count = 1;
	
	public ConcurrentObj() {super();}
	public ConcurrentObj(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
