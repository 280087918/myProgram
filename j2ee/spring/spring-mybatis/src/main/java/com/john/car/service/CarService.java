package com.john.car.service;

import java.util.List;

import com.john.car.vo.CarVo;

public interface CarService {
	public void saveCar(CarVo carVo);
	
	public List<CarVo> listCars();
	
	public List<CarVo> listPageCars(int page, int rows);
	
	public void rollbackCar(CarVo carVo) throws Exception ;
}
