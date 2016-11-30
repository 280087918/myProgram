package com.john.system.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskAlreadyCount {
	
	@Scheduled(cron="0/2 * *  * * ? ")//2s执行一次
	public void udAlreadyCount() {
		System.out.println("gogogogo");
	}
}
