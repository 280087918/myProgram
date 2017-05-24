package com.john.beanpostprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MyServiceImpl {
	Logger log = LoggerFactory.getLogger(MyServiceImpl.class);
	
	public MyServiceImpl() {
		log.info("MyServiceImpl init......");
	}
	
	@Transactional
	public void save() {
		log.info("object saved......");
	}
	
	public void query() {
		log.info("object query......");
	}
}
