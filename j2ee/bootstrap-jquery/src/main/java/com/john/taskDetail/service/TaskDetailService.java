package com.john.taskDetail.service;

import com.john.task.vo.TaskVo;

public interface TaskDetailService {
	/**
	 * 获取当天的签到百分比
	 * @param taskVo
	 * @return
	 */
	public Float obtainSignPercentage(TaskVo taskVo);
}
