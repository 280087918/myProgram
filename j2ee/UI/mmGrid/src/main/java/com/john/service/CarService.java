package com.john.service;

import java.util.List;

import com.john.dto.Car;

public interface CarService {
	public void saveCar(Car car);
	
	public List<Car> listCars();
}
