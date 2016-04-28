package com.john.controller.index;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.john.controller.BasicController;
import com.john.dao.ProductDao;
import com.john.vo.Product;

@Controller
public class IndexContrller extends BasicController {
	@Autowired
	private ProductDao productDao;
	
	@RequestMapping("index")
	public String index() {
		return "search_index";
	}
	
	@RequestMapping("searchTips")
	@ResponseBody
	public String searchTips(String keyword) {
		List<String> tipList = null;
		if(StringUtils.isNotBlank(keyword)) {
			tipList = productDao.searchTips(keyword);
		}
		Gson gson = new Gson();
		return gson.toJson(tipList);
	}
	
	@RequestMapping("search")
	@ResponseBody
	public String search(String keyword) {
		List<Product> products = productDao.searchList(keyword);
		Gson gson = new Gson();
		return gson.toJson(products);
	}
}
