package com.john.system.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.john.task.service.TaskService;

@Service
@Lazy(false)
public class TaskAlreadyCount {
	
	@Autowired
	private TaskService taskService;
	
//	@Scheduled(cron="0/2 * *  * * ? ")//2m执行一次
	@Scheduled(cron="0 55 23  * * ? ")//每晚23:55分执行
	public void udAlreadyCount() {
		taskService.updateAlreadyCount();
	}
}
