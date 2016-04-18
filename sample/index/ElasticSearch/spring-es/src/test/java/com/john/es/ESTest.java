package com.john.es;

import java.util.Iterator;

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
		u.setId("2");
		u.setUserName("haocheng");
		u.setAge(20);
		userRepository.save(u);
	}
	
	@Test
	public void testFind() {
		Iterable<User> users = userRepository.findAll();
		Iterator<User> ui = users.iterator();
		while(ui.hasNext()) {
			log.info("" + ui.next());
		}
	}
	
	@Test
	public void selfDel() {
		userRepository.deleteByAge(20);
	}
}
