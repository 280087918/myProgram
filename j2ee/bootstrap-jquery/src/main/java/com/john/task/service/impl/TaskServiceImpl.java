package com.john.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.task.dao.TaskMapper;
import com.john.task.service.TaskService;
import com.john.task.vo.TaskVo;
import com.john.taskDetail.service.TaskDetailService;
import com.john.taskDetail.vo.TaskDetailVo;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskMapper taskMapper;
	
	@Autowired
	private TaskDetailService taskDetailService;
	
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
	
	@Override
	public void clearAlreadyCount(String taskId) {
		taskMapper.clearAlreadyCount(taskId);
	}
	
	@Override
	public void updateAlreadyCount() {
		Map<String, Object> taskParams = new HashMap<String, Object>();
		taskParams.put("currentDate", DateTime.now().withMillisOfDay(0).toDate());
		List<TaskVo> taskVos = taskMapper.findValidTask(taskParams);
		if(CollectionUtils.isNotEmpty(taskVos)) {
			Map<String, Object> detailParams = new HashMap<String, Object>();
			List<TaskDetailVo> taskDetailVos = null;
			for(TaskVo taskVo : taskVos) {
				detailParams.clear();
				detailParams.put("taskId", taskVo.getId());
				detailParams.put("beginDate", DateTime.now().withMillisOfDay(0).toDate());
				detailParams.put("endDate", DateTime.now().withMillisOfDay(0).plusDays(1).toDate());
				detailParams.put("isSign", 0);
				taskDetailVos = taskDetailService.findDetailByTime(detailParams);
				if(CollectionUtils.isEmpty(taskDetailVos)) {//为空的情况下，证明当天没有清除记录，可以+1
					taskMapper.addAlreadyCount(taskVo.getId());
				} else {
					logger.info("今天有未签到记录，已签到记录不+1");
				}
			}
		}
	}
}
