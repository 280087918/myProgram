package com.john.dao;

import java.util.List;

import com.john.dto.Car;

public interface CarDao {
	//保存
	void saveCar(Car car);
	
	//查询集合
	List<Car> queryCars();
}
