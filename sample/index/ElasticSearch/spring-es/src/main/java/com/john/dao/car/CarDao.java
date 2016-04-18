package com.john.dao.car;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.base.vo.PageVo;
import com.john.vo.Car;

public interface CarDao {
	Logger log = LoggerFactory.getLogger(CarDao.class);
	
	//保存单个文档
	public void saveDocCar(Car car);
	
	//删除单个文档
	public void delDocCar(String docId);
	
	//批量存储
	public void saveBatchDocCar(List<Car> cars);
	
	//查询分页数据
	public PageVo<Car> queryPage(PageVo<Car> page, Car searcher);
}
