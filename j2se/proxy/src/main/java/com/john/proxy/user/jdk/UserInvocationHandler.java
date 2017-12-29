package com.john.proxy.user.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserInvocationHandler implements InvocationHandler {
	
	private Object target;
	
	public UserInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result;
		System.out.println("log before method");
		result=method.invoke(target, args);
		System.out.println("log after method");
		return result;
	}

}
