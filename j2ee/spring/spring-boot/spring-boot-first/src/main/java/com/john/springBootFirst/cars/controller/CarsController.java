package com.john.springBootFirst.cars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.john.springBootFirst.cars.service.CarsService;

@RestController
@RequestMapping("cars")
public class CarsController {
	/**
	 * 这个接口没有按照常规的方式进行初始化
	 * 	是在InitCars里面通过@Bean注解做一个实现，让spring容器去管理起来
	 */
	@Autowired
	private CarsService carsService;
	
	@RequestMapping("list")
	public String list() {
		return "Hello World!" + carsService.getCars();
	}
}
