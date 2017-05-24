package com.john.test.car;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.car.service.CarService;
import com.john.car.vo.CarVo;
import com.john.test.BaseTestWithDB;
import com.john.utils.IdUtils;

public class CarSaveTest extends BaseTestWithDB {
	@Autowired
	private CarService carService;
	
	@Test
	public void save() {
		carService.saveCar(new CarVo(IdUtils.uuid(), "宝马", "i8"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "哈佛", "H6"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "福特", "锐界"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "大众", "途观"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "宝马", "X5"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "众泰", "T600"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "标志", "3008"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "奥迪", "Q5"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "丰田", "汉兰达"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "长安", "CS75"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "传祺", "GS4"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "比亚迪", "S6"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "本田", "CR-V"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "丰田", "雷凌"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "特斯拉", "Model-s"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "沃尔沃", "XC90混动"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "捷豹", "F-Type"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "阿斯顿马丁", "Rapide"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "福特", "野马"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "斯巴鲁", "BRZ"));
		carService.saveCar(new CarVo(IdUtils.uuid(), "雪佛兰", "科迈罗"));
	}
}
