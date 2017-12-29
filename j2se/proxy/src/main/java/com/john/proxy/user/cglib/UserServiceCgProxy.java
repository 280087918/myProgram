package com.john.proxy.user.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UserServiceCgProxy implements MethodInterceptor {
	private Object target;
	
	public Object getInstance(Object target) {
		this.target = target;  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());
        //回调方法  
        enhancer.setCallback(this);  
        //创建代理对象  
        return enhancer.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("log before user Add");
		
		proxy.invokeSuper(obj, args);
		
		System.out.println("log after user Add");
		return null;
	}

}
