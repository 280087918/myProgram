package com.john.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.Keyword;

public interface KeywordService {
	Logger logger = LoggerFactory.getLogger(KeywordService.class);
	
	public void persistObj(Keyword keyword);
	
	public void removeIndex();
	
	public void clearData();
	
	public void searchKeywords(Map<String, Object> params);
}
