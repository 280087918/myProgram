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
import com.john.system.utils.WebUtils;
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
	
	@Override
	public ResultVo sign(UserVo userVo) {
		ResultVo resultVo = new ResultVo();
		
		//任务信息
		Map<String, Object> taskParams = new HashMap<String, Object>();
		taskParams.put("createUser", userVo.getName());
		taskParams.put("currentDate", DateTime.now().toDate());
		TaskVo taskVo = taskService.obtainCurrenTask(taskParams);
		
		if(null != taskVo) {
			//时间跨度默认12个小时，从8点开始到20点结束
			Integer timePerSpan = 12 / taskVo.getPerDateCount();
			if((DateTime.now().getHourOfDay() - 8) >= 0) {//还没到8点，不能签到
				Integer timeSpan = (DateTime.now().getHourOfDay() - 8) / timePerSpan;//8点到现在总共间隔多少次的签到间隔
				DateTime beginDateTime = DateTime.now().withMillisOfDay(0).withHourOfDay(8 + timePerSpan * timeSpan);//开始时间区间
				DateTime endDateTime = DateTime.now().withMillisOfDay(0).withHourOfDay(8 + timePerSpan * timeSpan + timePerSpan);//结束时间区间
				logger.info("签到时间区间,beginDateTime:{}, endDateTime:{}", beginDateTime, endDateTime);
				
				Map<String, Object> signedParams = new HashMap<String, Object>();
				signedParams.put("taskId", taskVo.getId());
				signedParams.put("beginDate", beginDateTime.toDate());
				signedParams.put("endDate", endDateTime.toDate());
				List<TaskDetailVo> signedVos = taskDetailMapper.findSignDetailByTime(signedParams);
				if(signedVos.size() > 0) {
					resultVo.setStatus(Constants.ERROR);
					DateTime optDt = new DateTime(signedVos.get(0).getOptTime());
					
					resultVo.setMsg("您已经在[" + optDt.toString("yyyy-MM-dd HH:mm:ss") + "]签到过,请[" + timePerSpan + "]小时后再来");
					return resultVo;
				} else {
					//可以签到
					TaskDetailVo taskDetailVo = new TaskDetailVo();
					taskDetailVo.setId(WebUtils.uuid());
					taskDetailVo.setIsSign(1);
					taskDetailVo.setOptTime(DateTime.now().toDate());
					taskDetailVo.setSignUser(userVo.getName());
					taskDetailVo.setTaskId(taskVo.getId());
					taskDetailMapper.saveObj(taskDetailVo);
					
					resultVo.setStatus(Constants.SUCCESS);
					resultVo.setMsg("签到成功");
					return resultVo;
				}
			} else {
				resultVo.setStatus(Constants.ERROR);
				resultVo.setMsg("尚未到签到开始时间");
				return resultVo;
			}
		} else {
			resultVo.setStatus(Constants.ERROR);
			resultVo.setMsg("您当前没有有效任务.");
			return resultVo;
		}
	}
	
	@Override
	public ResultVo clear(UserVo userVo) {
		ResultVo resultVo = new ResultVo();
		
		//任务信息
		Map<String, Object> taskParams = new HashMap<String, Object>();
		taskParams.put("createUser", userVo.getName());
		taskParams.put("currentDate", DateTime.now().toDate());
		TaskVo taskVo = taskService.obtainCurrenTask(taskParams);
		
		if(null != taskVo) {
			TaskDetailVo taskDetailVo = new TaskDetailVo();
			taskDetailVo.setId(WebUtils.uuid());
			taskDetailVo.setIsSign(0);
			taskDetailVo.setOptTime(DateTime.now().toDate());
			taskDetailVo.setSignUser(userVo.getName());
			taskDetailVo.setTaskId(taskVo.getId());
			taskDetailMapper.saveObj(taskDetailVo);
			
			//清除主任务的已签到信息
			taskService.clearAlreadyCount(taskVo.getId());
			
			resultVo.setStatus(Constants.SUCCESS);
			resultVo.setMsg("清除");
			return resultVo;
		} else {
			resultVo.setStatus(Constants.ERROR);
			resultVo.setMsg("您当前没有有效任务.");
			return resultVo;
		}
	}
	
	@Override
	public List<TaskDetailVo> findDetailByTime(Map<String, Object> params) {
		List<TaskDetailVo> detailVos = null;
		detailVos = taskDetailMapper.findSignDetailByTime(params);
		return detailVos;
	}
}
