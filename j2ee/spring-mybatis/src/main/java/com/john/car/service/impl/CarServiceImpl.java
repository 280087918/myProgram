package com.john.car.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.john.car.dao.CarDaoMapper;
import com.john.car.service.CarService;
import com.john.car.vo.CarVo;

@Service
@Transactional
public class CarServiceImpl implements CarService {
	@Autowired
	private CarDaoMapper carDao;
	
	@Override
	public void saveCar(CarVo carVo) {
		carDao.saveCar(carVo);
	}
	
	@Override
	public List<CarVo> listCars() {
		return carDao.queryCars();
	}
}
