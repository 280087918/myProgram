package com.john.task.dao;

import java.util.List;
import java.util.Map;

import com.john.task.vo.TaskVo;

public interface TaskMapper {
	public List<TaskVo> findTasks(Map<String, Object> params);
}
