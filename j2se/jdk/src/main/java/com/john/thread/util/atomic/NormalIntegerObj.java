package com.john.thread.util.atomic;

public class NormalIntegerObj {
	private Integer count = null;
	
	public NormalIntegerObj(){super();}
	public NormalIntegerObj(int outCount) {
		count = outCount;
	}
	
	//传负数过来
	public int decr(int dec) {
		count = count + dec;
		return count.intValue();
	}
}
