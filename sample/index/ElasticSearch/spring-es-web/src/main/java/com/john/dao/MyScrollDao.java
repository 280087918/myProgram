package com.john.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.MyScroll;

public interface MyScrollDao {
	Logger log = LoggerFactory.getLogger(MyScrollDao.class);
	
	public void saveObject(MyScroll scroll);
	
	public List<MyScroll> searchList(String keyword);
}
