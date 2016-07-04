package com.john.test.transaction;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.car.service.CarService;
import com.john.car.vo.CarVo;
import com.john.test.BaseTest;
import com.john.utils.IdUtils;
public class TransactionTest  extends BaseTest {
	@Autowired
	CarService carService;
	
	@Test
	public void test() {
		CarVo car = new CarVo(IdUtils.uuid(), "大众", "甲壳虫");
//		CarVo car = new CarVo(IdUtils.uuid(), "雪弗兰", "大黄蜂");
		try {
			carService.rollbackCar(car);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
