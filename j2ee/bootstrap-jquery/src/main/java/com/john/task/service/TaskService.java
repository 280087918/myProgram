package com.john.task.service;

import java.util.Map;

import com.john.task.vo.TaskVo;

public interface TaskService {
	/**
	 * 获取当前有效的任务
	 * @param params
	 * @return
	 */
	public TaskVo obtainCurrenTask(Map<String, Object> params);
}
