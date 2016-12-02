package com.john.taskDetail.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.john.system.controller.BasicController;
import com.john.system.utils.Constants;
import com.john.system.utils.ResultVo;
import com.john.taskDetail.service.TaskDetailService;
import com.john.user.vo.UserVo;

@Controller
@RequestMapping("taskDetail")
public class TaskDetailController extends BasicController {
	@Autowired
	private TaskDetailService taskDetailService;
	
	@RequestMapping("sign")
	@ResponseBody
	public String sign(HttpServletRequest request) {
		ResultVo resultVo = null;
		Gson gson = new Gson();
		//判断是否已登录
		if(null != request.getSession() && null != request.getSession().getAttribute("loginUser")) {
			UserVo userVo = (UserVo) request.getSession().getAttribute("loginUser");
			resultVo = taskDetailService.sign(userVo);
			return gson.toJson(resultVo);
		} else {
			resultVo = new ResultVo();
			resultVo.setStatus(Constants.ERROR);
			resultVo.setMsg("请登录");
			return gson.toJson(resultVo);
		}
	}
	
	@RequestMapping("clear")
	@ResponseBody
	public String clear(HttpServletRequest request) {
		ResultVo resultVo = null;
		Gson gson = new Gson();
		//判断是否已登录
		if(null != request.getSession() && null != request.getSession().getAttribute("loginUser")) {
			UserVo userVo = (UserVo) request.getSession().getAttribute("loginUser");
			resultVo = taskDetailService.clear(userVo);
			return gson.toJson(resultVo);
		} else {
			resultVo = new ResultVo();
			resultVo.setStatus(Constants.ERROR);
			resultVo.setMsg("请登录");
			return gson.toJson(resultVo);
		}
	}
}
