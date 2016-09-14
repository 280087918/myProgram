package com.john.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.Category;

public interface CategoryDao {
	Logger log = LoggerFactory.getLogger(CategoryDao.class);
	
	public void saveCategory(Category category);
}
