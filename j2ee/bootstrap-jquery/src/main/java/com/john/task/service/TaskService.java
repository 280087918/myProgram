package com.john.task.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.task.vo.TaskVo;

public interface TaskService {
	public final static Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	/**
	 * 获取当前有效的任务
	 * @param params
	 * @return
	 */
	public TaskVo obtainCurrenTask(Map<String, Object> params);
	
	/**
	 * 根据taskId清除已签到信息
	 * @param taskId
	 */
	public void clearAlreadyCount(String taskId);
	
	/**
	 * 定时器用的，用来更新所有有效的已签到天数
	 */
	public void updateAlreadyCount();
}
