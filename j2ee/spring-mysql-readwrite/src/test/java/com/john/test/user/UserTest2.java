package com.john.test.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-base.xml"})
public class UserTest2 {
	Logger log = LoggerFactory.getLogger(UserTest2.class);
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testFind() {
		log.info("user{}", userService.findUser("9"));
	}
	
	@Test
	public void save() {
		UserVo user1 = new UserVo();
		user1.setId("11");
		user1.setName("12");
		user1.setAge(13);
		
		userService.saveUser(user1);
	}
}
