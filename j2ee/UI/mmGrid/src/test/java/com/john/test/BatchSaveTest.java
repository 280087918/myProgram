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
		Car car = new Car(StringUtils.uuid(), "¿ÆÂ³×È", "Ñ©·ðÀ¼", DateTime.now().plusDays(1).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "¿ÆÅÁÆæ", "Ñ©·ðÀ¼", DateTime.now().plusDays(2).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "ÂõÈñ±¦", "Ñ©·ðÀ¼", DateTime.now().plusDays(3).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "ÈüÅ·", "Ñ©·ðÀ¼", DateTime.now().plusDays(4).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "X1", "±¦Âí", DateTime.now().plusDays(5).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "X6", "±¦Âí", DateTime.now().plusDays(6).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "ÈñÖ¾", "·áÌï", DateTime.now().plusDays(7).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "¿­ÃÀÈð", "·áÌï", DateTime.now().plusDays(8).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "·É¶È", "±¾Ìï", DateTime.now().plusDays(9).toDate());
		carService.saveCar(car);
		
		car = new Car(StringUtils.uuid(), "H9", "¹þ·ð", DateTime.now().plusDays(10).toDate());
		carService.saveCar(car);
	}
}
