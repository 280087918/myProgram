package com.john.task;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzTask {
	protected Logger log = LoggerFactory.getLogger(QuartzTask.class);
	
	/**
	 * 这个看ThreadName可以看到名称为executor-1之类的，所以是用线程池的方式去实现的
	 * 相比@Scheduled不一样的是，因为是多线程，所以任务之间可以并行。
	 */
	public void start() {
		log.info("调度执行, ThreadName【{}】, currentTime【{}】", Thread.currentThread().getName(), new DateTime().toString());
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("完成, ThreadName【{}】, currentTime【{}】", Thread.currentThread().getName(), new DateTime().toString());
	}
}
