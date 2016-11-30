package com.john.task.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.task.dao.TaskMapper;
import com.john.task.service.TaskService;
import com.john.task.vo.TaskVo;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskMapper taskMapper;
	
	@Override
	public TaskVo obtainCurrenTask(Map<String, Object> params) {
		List<TaskVo> taskVos = taskMapper.findCurrentTask(params);
		if(CollectionUtils.isNotEmpty(taskVos)) {
			if(taskVos.size() == 1) {
				return taskVos.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
