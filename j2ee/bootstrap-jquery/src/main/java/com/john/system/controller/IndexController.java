package com.john.system.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.john.system.utils.Constants;
import com.john.system.utils.ResultVo;
import com.john.system.utils.WebUtils;
import com.john.task.service.TaskService;
import com.john.task.vo.TaskVo;
import com.john.taskDetail.service.TaskDetailService;
import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

@Controller
public class IndexController extends BasicController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskDetailService taskDetailService;
	
	@RequestMapping("index")
	public String index(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//判断是否已登录
		if(null != request.getSession() && null != request.getSession().getAttribute("loginUser")) {
			//用户信息
			UserVo userVo = (UserVo) request.getSession().getAttribute("loginUser");
			modelMap.put("userVo", userVo);
			
			//任务信息
			Map<String, Object> taskParams = new HashMap<String, Object>();
			taskParams.put("createUser", userVo.getName());
			taskParams.put("currentDate", DateTime.now().toDate());
			TaskVo taskVo = taskService.obtainCurrenTask(taskParams);
			
			//总的签到百分比
			Float totalPercent = (float) taskVo.getAlreadyDateCount() / taskVo.getTotalDateCount();
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数
			modelMap.put("totalPercent", Float.parseFloat(df.format(totalPercent)));
			
			//查询当天签到百分比
			if(null != taskVo) {
				Float datePercent = taskDetailService.obtainSignPercentage(taskVo);
				modelMap.put("datePercent", datePercent);
			}
		}
		return "system/index";
	}
	
	@RequestMapping("login")
	@ResponseBody
	public String login(ModelMap modelMap, HttpServletRequest request, String userName, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", userName);
		params.put("password", WebUtils.strToMd5(password));
		UserVo userVo = userService.findLoginUser(params);
		
		ResultVo resultVo = new ResultVo();
		Gson gson = new Gson();
		if(null != userVo) {
			//放到session里面
			request.getSession().setAttribute("loginUser", userVo);
			
			resultVo.setStatus(Constants.SUCCESS);
			resultVo.setJsonResult(gson.toJson(userVo));
		} else {
			resultVo.setStatus(Constants.ERROR);
		}
		return gson.toJson(resultVo);
	}
}
