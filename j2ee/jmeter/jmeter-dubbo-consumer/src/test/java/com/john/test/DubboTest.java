package com.john.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.john.api.AreaApi;
import com.john.dto.AreaDto;

public class DubboTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
		AreaApi areaApi = context.getBean(AreaApi.class);
		List<AreaDto> areas = areaApi.listAreas(10);
		System.out.println(areas);
	}
}
