package com.john.test.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.john.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-base.xml"})
public class UserBatchTest {
	
	Logger log = LoggerFactory.getLogger(UserBatchTest.class);
	
	static final int THREAD_COUNTS = 200;
	
	static int endCount = 0;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void batchTest() {
		Thread[] threads = new Thread[THREAD_COUNTS];
		
		//声明线程
		for (int i = 0; i < THREAD_COUNTS; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					int i = userService.saveBatch();
					endCount += i;
				}
			});
		}
		
		//启动线程
		for (int i = 0; i < THREAD_COUNTS; i++) {
			threads[i].start();
		}
		
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(THREAD_COUNTS == endCount) {
				break;
			}
		}
		log.info("执行完!");
	}
}
