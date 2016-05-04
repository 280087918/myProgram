package com.john.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.john.dto.Car;
import com.john.service.CarService;

@RequestMapping("index")
@Controller
public class IndexController extends BasicController {
	@Autowired
	private CarService carService;
	
	@RequestMapping("list")
	public String list(ModelMap model) {
		List<Car> cars = carService.listCars();
		model.put("cars", cars);
		return "car_list";
	}
}
