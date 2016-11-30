package com.john.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.john.system.utils.Constants;
import com.john.system.utils.ResultVo;
import com.john.system.utils.WebUtils;
import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

@Controller
public class IndexController extends BasicController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("index")
	public String index(ModelMap modelMap, HttpServletRequest request) throws Exception {
		if(null != request.getSession() && null != request.getSession().getAttribute("loginUser")) {
			UserVo userVo = (UserVo) request.getSession().getAttribute("loginUser");
			modelMap.put("userVo", userVo);
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
