package com.john.test;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dto.Car;
import com.john.service.CarService;
import com.john.utils.StringUtils;

public class BatchSaveTest extends BaseTestWithDB {
	@Autowired
	private CarService carService;
	
	@Test
	public void save() {
		Car car = new Car(StringUtils.uuid(), "��³��", "ѩ����", DateTime.now().plusDays(1).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "������", "ѩ����", DateTime.now().plusDays(2).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "����", "ѩ����", DateTime.now().plusDays(3).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "��ŷ", "ѩ����", DateTime.now().plusDays(4).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "X1", "����", DateTime.now().plusDays(5).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "X6", "����", DateTime.now().plusDays(6).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "��־", "����", DateTime.now().plusDays(7).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "������", "����", DateTime.now().plusDays(8).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "�ɶ�", "����", DateTime.now().plusDays(9).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "H9", "����", DateTime.now().plusDays(10).toDate());
		carService.saveCar(car);
	}
}
