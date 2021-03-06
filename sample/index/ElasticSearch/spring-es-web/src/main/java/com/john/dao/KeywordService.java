package com.john.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.Keyword;

public interface KeywordService {
	Logger logger = LoggerFactory.getLogger(KeywordService.class);
	
	public void persistObj(Keyword keyword);
	
	public void removeIndex();
	
	public void clearData();
	
	public Keyword findObj(String id);
	
	public void removeObjById(String id);
	
	public void searchKeywords(Map<String, Object> params);
	
	public List<String> searchBrandIds(String keyword);
}
