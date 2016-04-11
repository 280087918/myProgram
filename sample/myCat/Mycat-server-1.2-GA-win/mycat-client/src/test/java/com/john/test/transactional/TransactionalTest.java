package com.john.test.transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.test.BaseTestWithDB;
import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

/**
 * 这个测试类是用来测试事务控制的
 * 	看Mycat是否不存在事务控制
 * @author zhang.hc
 * @date 2016年4月11日 下午5:07:57
 */
public class TransactionalTest extends BaseTestWithDB {
	@Autowired
	private UserService userService;
	
	@Test//存储一条id为300的数据，看批量数据插入的时候会不会回滚id为300前的数据
	public void Save300() {
		UserVo u = new UserVo();
		u.setUserId(300);
		u.setDateTime(new Date());
		u.setReceiveAddress("事务测试");
		u.setProvinceCode("SX");
		List<UserVo> us = new ArrayList<UserVo>();
		us.add(u);
		userService.mutiSave(us);
	}
	
	@Test
	public void testSave() {
		List<UserVo> users = new ArrayList<UserVo>();
		UserVo userVo = null;
		for(int i = 1; i <= 800; i++) {
			userVo = new UserVo();
			userVo.setUserId(i);
			userVo.setDateTime(new Date());
			userVo.setReceiveAddress("广东省深圳市");
			userVo.setProvinceCode("GD");
			users.add(userVo);
		}
		userService.mutiSave(users);
		log.info("新增完成");
	}
	
	@Test//这个方法是方便我删除测试数据的
	public void delUsers() {
		userService.delUsers();
		log.info("测试数据清理完成");
	}
}
