package com.john.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这个是为了实现调度
 * @author zhang.hc
 * @date 2016年8月23日 上午9:33:10
 */
public class MyQuartz {
	protected Logger log = LoggerFactory.getLogger(MyQuartz.class);
			
	public void handle() {
		log.info("MyQuartz's handle method is running ......");
	}
}
