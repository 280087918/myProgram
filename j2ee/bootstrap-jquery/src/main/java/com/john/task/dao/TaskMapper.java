package com.john.task.dao;

import java.util.List;
import java.util.Map;

import com.john.task.vo.TaskVo;

public interface TaskMapper {
	public List<TaskVo> findTasks(Map<String, Object> params);
	
	/**
	 * 查询当前有效的任务，应该只有一个，如果有多个，那么上层请控制要提示相关错误信息
	 * @param params:createUser,currentDate
	 * @return
	 */
	public List<TaskVo> findCurrentTask(Map<String, Object> params);
}
