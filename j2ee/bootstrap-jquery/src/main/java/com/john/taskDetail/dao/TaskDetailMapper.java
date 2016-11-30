package com.john.taskDetail.dao;

import java.util.List;
import java.util.Map;

import com.john.taskDetail.vo.TaskDetailVo;

public interface TaskDetailMapper {
	public List<TaskDetailVo> findTaskDetails(Map<String, Object> params);
}
