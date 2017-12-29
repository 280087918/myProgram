package com.john.proxy;

import java.lang.reflect.Proxy;

import org.junit.Test;

import com.john.proxy.user.jdk.UserInvocationHandler;
import com.john.proxy.user.jdk.UserService;
import com.john.proxy.user.jdk.UserServiceImpl;

public class JdkProxyTest {
	
	@Test
	public void test() {
		UserService userService = new UserServiceImpl();
		UserInvocationHandler handler = new UserInvocationHandler(userService);
		UserService proxy = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(),
				new Class[]{UserService.class}, handler);
		proxy.save();
	}
}
