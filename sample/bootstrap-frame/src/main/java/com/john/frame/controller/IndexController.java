package com.john.frame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {
	@RequestMapping("index")
	public String index() {
		log.info("系统初始化成功......");
		return "index";
	}
}
