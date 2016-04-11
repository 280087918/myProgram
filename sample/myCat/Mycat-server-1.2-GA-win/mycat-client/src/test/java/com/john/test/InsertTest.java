package com.john.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.user.dao.UserDaoMapper;
import com.john.user.vo.UserVo;

public class InsertTest extends BaseTestWithDB {
	@Autowired
	private UserDaoMapper userDao;
	
	@Test
	public void delAll() {
		userDao.clearAll();
	}
	
	@Test
	public void save1() {
		UserVo userVo = null;
		userVo = new UserVo();
		userVo.setUserId(1);
		userVo.setDateTime(new Date());
		userVo.setReceiveAddress("广东省深圳市");
		userVo.setProvinceCode("GD");
		userDao.saveUser(userVo);
	}
	
	@Test
	public void saveTest1() {
		UserVo userVo = null;
		for(int i = 1; i <= 1200; i++) {
			userVo = new UserVo();
			userVo.setUserId(i);
			userVo.setDateTime(new Date());
			userVo.setReceiveAddress("广东省深圳市");
			userVo.setProvinceCode("GD");
			userDao.saveUser(userVo);
		}
	}
}
