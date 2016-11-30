package com.john.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.john.system.controller.BasicController;
import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

@Controller
@RequestMapping("user")
public class UserController extends BasicController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	public String list(ModelMap modelMap) {
		List<UserVo> users = userService.listUser();
		logger.info("users{}", users);
		modelMap.put("users", users);
		return "user/list";
	}
}
