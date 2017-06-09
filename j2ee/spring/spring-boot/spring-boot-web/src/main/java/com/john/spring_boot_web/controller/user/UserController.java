package com.john.spring_boot_web.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.john.spring_boot_web.entity.User;

@RestController
@RequestMapping(value="/user")
public class UserController {
	@RequestMapping(value="/get")
	public User getUser() {
		return new User("张三", "hcpwd001");
	}
}
