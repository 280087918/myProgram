package com.john.controller.users;

import org.springframework.web.bind.annotation.RequestMapping;

public class UsersController {
	@RequestMapping("/userList")
	public String userList() {
		return "/user/userList";
	}
	
	
}
