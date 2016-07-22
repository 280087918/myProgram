package com.john.car.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.john.car.dao.CarDaoMapper;
import com.john.car.service.CarService;
import com.john.car.vo.CarVo;
import com.john.utils.MyException;

@Service
public class CarServiceImpl implements CarService {
	@Autowired
	private CarDaoMapper carDao;
	
	@Override
	@Transactional
	public void saveCar(CarVo carVo) {
		carDao.saveCar(carVo);
	}
	
	@Override
	public List<CarVo> listCars() {
		return carDao.queryCars();
	}
	
	@Override
	public List<CarVo> listPageCars(int page, int rows) {
		//分页查询
        PageHelper.startPage(page, rows);
        return carDao.queryCars();
	}
	
	@Override
	@Transactional(rollbackFor=MyException.class)
	public void rollbackCar(CarVo carVo) throws Exception {
		
		carDao.saveCar(carVo);
		
		if(carVo.getModel().equals("甲壳虫")) {
			throw new MyException("不能存甲壳虫");
		}
	}
}
