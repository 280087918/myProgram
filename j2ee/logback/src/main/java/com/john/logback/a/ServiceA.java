package com.john.logback.a;

import lombok.ToString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ToString
public class ServiceA {
	Logger log = LoggerFactory.getLogger(ServiceA.class);
	
	public ServiceA() {
		log.info("ServiceA log <info>");
		log.debug("ServiceA log <debug>");
		log.warn("ServiceA log <warn>");
		log.error("ServiceA log <error>");
	}
}
