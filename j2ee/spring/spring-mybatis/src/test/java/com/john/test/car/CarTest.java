package com.john.test.car;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
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
	
	@Test
	public void testPage() {
		int page = 1;
		int rows = 5;
		List<CarVo> carList = carService.listPageCars(page, rows);
		PageInfo<CarVo> pageInfo = new PageInfo<CarVo>(carList);
		//这个是整个语句放内存里面执行一次，看有多少记录行数，不是用count的
		log.info("::::{}", pageInfo);
		log.info("::{}" + pageInfo.getList().size());
	}
}
