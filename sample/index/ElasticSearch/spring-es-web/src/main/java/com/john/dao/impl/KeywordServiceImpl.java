package com.john.dao.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.john.dao.KeywordService;
import com.john.vo.Keyword;

@Service
public class KeywordServiceImpl implements KeywordService {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@PostConstruct
	public void init() {
		if(!elasticsearchTemplate.indexExists(Keyword.class)) {
			elasticsearchTemplate.createIndex(Keyword.class);
		}
		elasticsearchTemplate.putMapping(Keyword.class);
	}
	
	@Override
	public void persistObj(Keyword keyword) {
		IndexQuery index = new IndexQueryBuilder().withId(keyword.getId()).withObject(keyword).build();
		elasticsearchTemplate.putMapping(Keyword.class);
		String result = elasticsearchTemplate.index(index);
		logger.info("saved({})", result);
	}
	
	@Override
	public void removeIndex() {
		if(elasticsearchTemplate.indexExists(Keyword.class.getAnnotation(Document.class).indexName()))
			elasticsearchTemplate.deleteIndex(Keyword.class.getAnnotation(Document.class).indexName());
	}
	
	@Override
	public void clearData() {
		DeleteQuery deleteQuery = new DeleteQuery();
		
		QueryBuilder qb = QueryBuilders.matchAllQuery();
		deleteQuery.setQuery(qb);
		deleteQuery.setIndex(Keyword.class.getAnnotation(Document.class).indexName());
		deleteQuery.setType(Keyword.class.getAnnotation(Document.class).type());
		
		elasticsearchTemplate.delete(deleteQuery);
	}
	
	@Override
	public void searchKeywords(Map<String, Object> params) {
		String keyword = (String) params.get("keyword");
		String searchId = params.get("searchId") == null ? null : (String) params.get("searchId");  
		
		logger.info("keyword={}", keyword);
		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
		
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		if(StringUtils.isNotBlank(keyword)) {
			keyword = keyword.trim();
			qb.must(queryStringQuery(" name:" + keyword));
		}
		
		if(StringUtils.isNotBlank(searchId)) {
			qb.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.termQuery("id", searchId))
					.should(QueryBuilders.termQuery("parentIds", searchId))
					);
		}
		searchQuery.withQuery(qb);
		
		Page<Keyword> page = elasticsearchTemplate.queryForPage(searchQuery.build(), Keyword.class);
		logger.info("搜索记录条数:({})", page.getTotalElements());
		if(page.getTotalElements() > 0) {
			for(Keyword k : page.getContent()) {
				logger.info("{}", k);
			}
		}
	}
}
