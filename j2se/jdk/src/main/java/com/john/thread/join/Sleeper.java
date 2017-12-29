package com.john.thread.join;

public class Sleeper extends Thread {
	private int duration;
	
	public Sleeper(String name, int sleeptime) {
		super(name);
		this.duration = sleeptime;
		start();//在构造器当中就启动线程
	}
	
	@Override
	public void run() {
		try {
			sleep(duration);
		} catch (InterruptedException e) {
			//异常捕获时interrupt标识会被寝宫，所以为什么输出是false
			System.out.println(getName() + " was interrupted. isInterrupted():" + isInterrupted());
		}
		System.out.println(getName() + " has awakened.");
	}
}
