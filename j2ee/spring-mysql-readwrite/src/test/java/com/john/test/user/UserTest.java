package com.john.test.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.test.BaseTestWithDB;
import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

/**
 * 这个单元测试有问题，不能实现切换
 * @author zhang.hc
 * @date 2016年6月27日 下午3:32:25
 */
public class UserTest extends BaseTestWithDB {
	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		log.info("users{}", userService.listUser());
	}
	
	@Test
	public void testFind() {
		log.info("user{}", userService.findUser("9"));
	}
	
	@Test
	public void save() {
		UserVo user1 = new UserVo();
		user1.setId("10");
		user1.setName("11");
		user1.setAge(12);
		
		userService.saveUser(user1);
	}
}
