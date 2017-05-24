package com.john.car.dao;

import java.util.List;

import com.john.car.vo.CarVo;

public interface CarDaoMapper {
	void saveCar(CarVo carVo);
	
	List<CarVo> queryCars();
}
