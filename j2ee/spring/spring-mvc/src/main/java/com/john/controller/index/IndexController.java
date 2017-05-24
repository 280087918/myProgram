package com.john.controller.index;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.john.controller.BasicController;

/**
 * 首页
 * @author zhang.hc
 * @date 2016年5月13日 上午11:11:21
 */
@Controller
public class IndexController extends BasicController {
	
	@RequestMapping(value="index")
	public String index(ModelMap modelMap, HttpServletRequest request) {
		log.info("index ......");
		return "system/index";
	}
	
	@RequestMapping("ex")
	public String ex() throws Exception {
		int i = 1 / 0;
		return "system/index";
	}
}
