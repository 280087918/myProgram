package com.john.dao.impl;

import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.john.dao.AService;
import com.john.vo.A;

@Service
public class AServiceImpl implements AService {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private Client esClient;
	
	@PostConstruct
	public void init() {
//		if(!elasticsearchTemplate.indexExists(A.class)) {
//			elasticsearchTemplate.createIndex(A.class);
//		}
//		elasticsearchTemplate.putMapping(A.class);
	}
	
	@Override
	public void saveA(A a) {
		IndexQuery index = new IndexQueryBuilder().withId(a.getId()).withObject(a).build();
		String result = elasticsearchTemplate.index(index);
		log.info("存储结果:" + result);
	}
	
	@Override
	public A findAById(String id) {
		Criteria criteria = Criteria.where("id").is(id);
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
		
		A a = elasticsearchTemplate.queryForObject(criteriaQuery, A.class);
		return a;
	}
	
	@Override
	public A findASimiJoin(String bId) {
		QueryBuilder builder = nestedQuery("bs", boolQuery().must(termQuery("bs.id", bId)));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
		
		List<A> as = elasticsearchTemplate.queryForList(searchQuery, A.class);
		return CollectionUtils.isNotEmpty(as) ? as.get(0) : null;
	}
	
	@Override
	public A findAsimiJoinS(String s) {
		//QueryBuilder builder = nestedQuery("ss", boolQuery().must(termQuery("ss", s)));
		QueryBuilder builder = termQuery("ss", s);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
		
		List<A> as = elasticsearchTemplate.queryForList(searchQuery, A.class);
		return CollectionUtils.isNotEmpty(as) ? as.get(0) : null;
	}
}
