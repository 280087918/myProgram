package com.john.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {
	private static ApplicationContext context = null;
	
	public synchronized static ApplicationContext obtainContext() {
		if(null == context)
			context = new ClassPathXmlApplicationContext("beans.xml");
		return context;
	}
}
