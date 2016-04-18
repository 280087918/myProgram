package com.john.Synchronized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方法锁，要是同一个对象里面有多个方法锁，那么锁之间会互斥。
 * 	就是好比这里同一个对象的add方法和update也是线程同步的，锁相当于放到this上面
 * @author zhang.hc
 * @date 2016年4月18日 上午11:04:44
 */
public class User {
	private Logger log = LoggerFactory.getLogger(User.class);
	
	public User() {}
	
	public synchronized void add() {
		try {
			Thread.sleep(2000);
//			this.wait(2000);
			log.info("add a user");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void update() {
		log.info("update a user");
	}
}
