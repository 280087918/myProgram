package com.john.test.car;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.car.service.CarService;
import com.john.car.vo.CarVo;
import com.john.test.BaseTestWithDB;
import com.john.utils.IdUtils;

public class CarTest extends BaseTestWithDB {
	@Autowired
	private CarService carService;
	
	@Test
	public void test() {
		log.info("cars{}", carService.listCars());
	}
	
	@Test
	public void save() {
		CarVo carVo1 = new CarVo();
		carVo1.setId(IdUtils.uuid());
		carVo1.setName("雪弗兰");
		carVo1.setModel("科鲁兹");
		
		CarVo carVo2 = new CarVo();
		carVo2.setId(IdUtils.uuid());
		carVo2.setName("大众");
		carVo2.setModel("甲壳虫");
		
		carService.saveCar(carVo1);
		carService.saveCar(carVo2);
	}
}
