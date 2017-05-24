package com.john;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@org.springframework.stereotype.Component
public class Component {
	private int count = 0;
	public Component() {
		System.out.println("component init......");
	}
	
	public int incr() {
		return count ++;
	}
	
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
			Component component = context.getBean(Component.class);
			System.out.println(component.incr());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
