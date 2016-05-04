package com.john.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.dao.CarDao;
import com.john.dto.Car;
import com.john.service.CarService;

@Service
public class CarServiceImpl implements CarService {
	@Autowired
	private CarDao carDao;

	@Override
//	@Transactional
	public void saveCar(Car car) {
		carDao.saveCar(car);
	}

	@Override
	public List<Car> listCars() {
		return carDao.queryCars();
	}

}
