package com.john.dao.impl;

import javax.annotation.PostConstruct;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.john.dao.CategoryDao;
import com.john.vo.Category;

@Service
public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private Client esClient;
	
	@PostConstruct
	public void init() {
		if(!elasticsearchTemplate.indexExists(Category.class)) {
			elasticsearchTemplate.createIndex(Category.class);
		}
		
		elasticsearchTemplate.putMapping(Category.class);
	}
	
	@Override
	public void saveCategory(Category category) {
		IndexQuery index = new IndexQueryBuilder().withId(category.getId()).withObject(category).build();
		String result = elasticsearchTemplate.index(index);
		log.info("存储结果:" + result);
	}
}
