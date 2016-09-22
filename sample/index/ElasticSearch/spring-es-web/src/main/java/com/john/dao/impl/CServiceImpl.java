package com.john.dao.impl;

import javax.annotation.PostConstruct;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.john.dao.CService;
import com.john.vo.C;

/**
 * 这个主要用来测试系统初始化的时候各种类型匹配不上的问题
 * @author zhang.hc
 * @date 2016年9月21日 下午5:11:47
 */
@Service
public class CServiceImpl implements CService {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private Client esClient;
	
	@PostConstruct
	public void init() {
//		if(!elasticsearchTemplate.indexExists(C.class)) {
//			elasticsearchTemplate.createIndex(C.class);
//		}
//		elasticsearchTemplate.putMapping(C.class);
	}

	@Override
	public void saveC(C c) {
		IndexQuery index = new IndexQueryBuilder().withId(c.getId()).withObject(c).build();
		String result = elasticsearchTemplate.index(index);
		log.info("存储结果:" + result);
	}

	@Override
	public C findCById(String id) {
		Criteria criteria = Criteria.where("id").is(id);
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
		
		C c = elasticsearchTemplate.queryForObject(criteriaQuery, C.class);
		return c;
	}

}
