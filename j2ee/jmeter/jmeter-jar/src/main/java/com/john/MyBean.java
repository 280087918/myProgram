package com.john;

public class MyBean {
	private static MyBean myBean = null;
	
	private int i = 0;
	
	public synchronized static MyBean obtainBean() {
		if(null == myBean)
			myBean = new MyBean();
		return myBean;
	}
	
	public int incrI() {
		return i ++;
	}
}
