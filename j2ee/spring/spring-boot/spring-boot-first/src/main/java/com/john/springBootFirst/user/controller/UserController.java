package com.john.springBootFirst.user.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.john.springBootFirst.system.Config;
import com.john.springBootFirst.user.service.UserService;

/**
 * @RestController:spring会直接返回字符串给caller。因为他是Rest
 * 	所以方法声明不用加@ResponseBody
 * @EnableAutoConfiguration 告诉spring-boot根据Starter去自动配置
 * @author Administrator
 *
 */
//@Controller
@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	/*
	 * 新鲜玩意儿，不过没有@Autowired看起来代码简洁
	 * 因为是final的所以构造器必须初始化这个userService,这样子就给了spring初始化这个对象的一个合理的入口
	 */
	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Value("${app.version}")//这样可以拿到.properties设置好的属性
	private String appVersion;
	
//	@Autowired
	@Resource(name="my-com.john.springBootFirst.system.Config")//可以看SpringConfiguration,Config已经注入到配置里面，bean的命名就是这个规则
	private Config config;
	
	@Value("${my.name}")//从yml文件中读取,@Value does not support relaxed binding.
	private String sysName;
	
	@Value("${mysql-url}")
	private String mysqlUrl;
	
	@RequestMapping("/list")
//    @ResponseBody
    public String list() {
		logger.info("my.name:{}, mysqlUrl:{}", sysName, mysqlUrl);
		
		userService.addUser();
		System.out.println("Config:" + config);
        return "Hello World!-user list!, appVersion:" + appVersion;
    }
	
	@RequestMapping("/log")
	public String log() {
		logger.debug("This is a debug message");  
        logger.info("This is an info message");  
        logger.warn("This is a warn message");  
        logger.error("This is an error message");
		return "Hello John";
	}
}
