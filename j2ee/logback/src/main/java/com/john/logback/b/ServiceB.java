package com.john.logback.b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceB {
	Logger log = LoggerFactory.getLogger(ServiceB.class);
	
	public ServiceB() {
		log.info("ServiceB log <info>");
		log.debug("ServiceB log <debug>");
		log.warn("ServiceB log <warn>");
		log.error("ServiceB log <error>");
	}
}
