package com.john.springBootFirst.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.john.springBootFirst.cars.service.CarsService;

@Component
public class InitCars {
	
	@Bean
	public CarsService implCarsService() {
		return new CarsService() {
			@Override
			public List<String> getCars() {
				List<String> cars = new ArrayList<String>();
				cars.add("audi");
				cars.add("BMW");
				cars.add("chevrolet");
				return cars;
			}
		};
	}
}
