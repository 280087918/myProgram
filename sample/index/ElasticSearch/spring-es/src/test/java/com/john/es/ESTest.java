package com.john.es;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.repositorys.UserRepository;
import com.john.test.BaseTest;
import com.john.vo.User;

public class ESTest extends BaseTest {
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testSave() {
		User u = new User();
		u.setId("1");
		u.setUserName("taozi");
		u.setAge(20);
		userRepository.save(u);
	}
}
