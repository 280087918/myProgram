package com.john.Synchronized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象内部锁。指定一个对象作为锁，这个在spring源码里面应用很广泛
 * 	意思是在对象内部本身，可以设置一把锁，防止资源竞争
 * @author zhang.hc
 * @date 2016年4月18日 上午11:06:31
 */
public class Car {
	private Logger log = LoggerFactory.getLogger(Car.class);
	private Object obj = new Object();
	
	public void add() {
		synchronized(obj) {
			try {
//				this.wait(2000);//这里用waitJVM会直接抛出异常，因为JVM认为你本身在锁里面去释放锁是不合理的。
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("add a car");
		}
	}
	
	public void update() {
		synchronized(obj) {
			log.info("update a car");
		}
	}
}
