package com.john.thread.runnable;

public class LiftOff implements Runnable {
	
	protected int countDown = 10;
	private static int taskCount = 1;
	
	/**
	 * 1.因为taskCount是static的，内存里面只有一份，所以会一直加上去
	 * 2.另外这个final加上之后，才会变成非后台线程。如果不加的话，相当于后台线程。线程没跑完就会中断。(目前还不知道原理)
	 */
	private final int id = taskCount ++;
	public LiftOff() {}
	public LiftOff(int countDown) {
		this.countDown = countDown;
	}
	public String status() {
		return Thread.currentThread().getName() + ":  #" + id + "(" + (countDown > 0 ? countDown : "liftOff!") + "), ";
	}

	@Override
	public void run() {
		while(countDown-- > 0) {
			System.out.println(status());
			Thread.yield();
		}
	}
}
