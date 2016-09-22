package com.john.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.A;

public interface AService {
	Logger log = LoggerFactory.getLogger(AService.class);
	
	public void saveA(A a);
	
	public A findAById(String id);
	
	public A findASimiJoin(String bId);
}
