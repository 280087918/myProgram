package com.john.spring_boot_web.controller.index;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/welcome")
	public String welcome(ModelMap model) {
		model.put("time", new Date());
		model.put("msg", "Jonathan.Chang");
		return "welcome";
	}
}
