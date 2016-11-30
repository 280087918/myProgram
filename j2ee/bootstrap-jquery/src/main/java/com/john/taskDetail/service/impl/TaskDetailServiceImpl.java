package com.john.taskDetail.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.system.utils.Constants;
import com.john.system.utils.ResultVo;
import com.john.task.service.TaskService;
import com.john.task.vo.TaskVo;
import com.john.taskDetail.dao.TaskDetailMapper;
import com.john.taskDetail.service.TaskDetailService;
import com.john.taskDetail.vo.TaskDetailVo;
import com.john.user.vo.UserVo;

@Service
public class TaskDetailServiceImpl implements TaskDetailService {
	@Autowired
	private TaskDetailMapper taskDetailMapper;
	
	@Autowired
	private TaskService taskService;
	
	@Override
	public Float obtainSignPercentage(TaskVo taskVo) {
		//获取当天最后一条清除的记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskVo.getId());
		params.put("currentDate", DateTime.now().withMillisOfDay(0).toDate());//当天零点
		TaskDetailVo taskDetailVo = taskDetailMapper.findCurrentLastUnsignDetail(params);
		System.out.println(taskDetailVo);
		
		if(null == taskDetailVo) {//当天位置还没有清除记录，那么直接取凌晨到现在签到的记录条数即可
			params.put("requestDate", DateTime.now().withMillisOfDay(0).toDate());
		} else {
			params.put("requestDate", taskDetailVo.getOptTime());
		}
		
		//获取最后一条清除记录后的签到集合
		List<TaskDetailVo> signDetails = taskDetailMapper.findCurrentSignDetail(params);
		Float percent = (float) signDetails.size() / taskVo.getPerDateCount();
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数
		
		percent = Float.parseFloat(df.format(percent));
		return percent;
	}
	
	public ResultVo sign(UserVo userVo) {
		ResultVo resultVo = new ResultVo();
		
		//任务信息
		Map<String, Object> taskParams = new HashMap<String, Object>();
		taskParams.put("createUser", userVo.getName());
		taskParams.put("currentDate", DateTime.now().toDate());
		TaskVo taskVo = taskService.obtainCurrenTask(taskParams);
		
		//时间跨度默认12个小时，从8点开始到20点结束
		Float timeSpan = 12f / taskVo.getPerDateCount();
		if((DateTime.now().getHourOfDay() - 8) >= 0) {//还没到8点，不能签到
			
		} else {
			resultVo.setStatus(Constants.ERROR);
			resultVo.setMsg("尚未到签到开始时间");
			return resultVo;
		}
		return null;
	}
}
