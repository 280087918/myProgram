package com.john.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.C;

public interface CService {
	Logger log = LoggerFactory.getLogger(CService.class);
	
	public void saveC(C c);
	
	public C findCById(String id);
}
