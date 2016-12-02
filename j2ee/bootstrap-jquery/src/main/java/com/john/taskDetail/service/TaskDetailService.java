package com.john.taskDetail.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.system.utils.ResultVo;
import com.john.task.vo.TaskVo;
import com.john.taskDetail.vo.TaskDetailVo;
import com.john.user.vo.UserVo;

public interface TaskDetailService {
	public final static Logger logger = LoggerFactory.getLogger(TaskDetailService.class);
	
	/**
	 * 获取当天的签到百分比
	 * @param taskVo
	 * @return
	 */
	public Float obtainSignPercentage(TaskVo taskVo);
	
	/**
	 * 签到
	 * @param userVo
	 * @return
	 */
	public ResultVo sign(UserVo userVo);
	
	/**
	 * 清除
	 * @param userVo
	 * @return
	 */
	public ResultVo clear(UserVo userVo);
	
	/**
	 * 根据条件获取任务明细。(时间区间)
	 * @param params
	 * @return
	 */
	public List<TaskDetailVo> findDetailByTime(Map<String, Object> params);
}
