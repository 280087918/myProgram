package com.john.taskDetail.dao;

import java.util.List;
import java.util.Map;

import com.john.taskDetail.vo.TaskDetailVo;

public interface TaskDetailMapper {
	public List<TaskDetailVo> findTaskDetails(Map<String, Object> params);
	
	/**
	 * 查找某个时间段之后最后一条清除记录
	 * @param params
	 * @return
	 */
	public TaskDetailVo findCurrentLastUnsignDetail(Map<String, Object> params);
	
	/**
	 * 查找某个时间段后的签到记录集合
	 * @param params
	 * @return
	 */
	public List<TaskDetailVo> findCurrentSignDetail(Map<String, Object> params);
	
	/**
	 * 根据时间区间查找是否有已经签到的记录
	 * @param params taskId beginDate endDate
	 * @return
	 */
	public List<TaskDetailVo> findSignDetailByTime(Map<String, Object> params);
	
	/**
	 * 保存签到明细对象
	 */
	public void saveObj(TaskDetailVo taskDetailVo);
}
