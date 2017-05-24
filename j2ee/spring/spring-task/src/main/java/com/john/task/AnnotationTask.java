package com.john.task;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
@Lazy(false)
public class AnnotationTask {
	protected Logger log = LoggerFactory.getLogger(AnnotationTask.class);
	
	/**
	 * 这里每隔多长时间执行，是从上一次调度完成后的时间间隔。如果上一次调度没有执行成功完，下一次就不会执行。
	 */
	//@Scheduled(cron="*/5 * * * * ?")
	public void run() {
		log.info("5秒执行一次, ThreadName【{}】, currentTime【{}】", Thread.currentThread().getName(), new DateTime().toString());
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("调度执行完成,ThreadName【{}】, currentTime【{}】", Thread.currentThread().getName(), new DateTime().toString());
	}
}
