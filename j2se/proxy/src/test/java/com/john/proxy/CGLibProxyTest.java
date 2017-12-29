package com.john.proxy;

import org.junit.Test;

import com.john.proxy.user.cglib.UserServiceCG;
import com.john.proxy.user.cglib.UserServiceCgProxy;

public class CGLibProxyTest {
	@Test
	public void test() {
		UserServiceCgProxy proxy = new UserServiceCgProxy();
		UserServiceCG userServieCg = (UserServiceCG) proxy.getInstance(new UserServiceCG());
		userServieCg.add();
	}
}
