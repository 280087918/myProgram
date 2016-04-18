package com.john.es;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.base.vo.PageVo;
import com.john.dao.car.CarDao;
import com.john.test.BaseTest;
import com.john.vo.Car;

public class ESTemplateTest extends BaseTest {
	@Autowired
	private CarDao carDao;
	
	@Test//添加单个
	public void saveSingleTest() {
		Car car = new Car();
		car.setId("001");
		car.setBrandName("雪佛兰");
		car.setSeries("大黄蜂");
		car.setAreaType("欧美");
		car.setProdDate(new Date());
		
		carDao.saveDocCar(car);
	}
	
	@Test
	public void saveBatchTest() {
		List<Car> cars = new ArrayList<Car>();
		Car car1 = new Car();
		car1.setId("004");
		car1.setBrandName("保时捷");
		car1.setSeries("911");
		car1.setAreaType("欧美");
		car1.setProdDate(new Date());
		cars.add(car1);
		
		Car car2 = new Car();
		car2.setId("005");
		car2.setBrandName("本田");
		car2.setSeries("飞度");
		car2.setAreaType("日本");
		car2.setProdDate(new Date());
		cars.add(car2);
		
		carDao.saveBatchDocCar(cars);
	}
	
	@Test//删除
	public void delSingleTest() {
		carDao.delDocCar("AVQoHvvf4Va0l0grjz8p");
	}
	
	@Test//分页数据查询
	public void pageTest1() {
		PageVo<Car> page = new PageVo<Car>();
		Car searcher = new Car();
		searcher.setBrandName("雪弗兰");
		carDao.queryPage(page, searcher);
	}
}
