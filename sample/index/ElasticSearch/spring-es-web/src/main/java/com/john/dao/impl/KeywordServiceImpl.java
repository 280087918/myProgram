package com.john.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
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
	public Keyword findObj(String id) {
		Criteria criteria = Criteria.where("id").is(id);
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
		
		Keyword keyword = elasticsearchTemplate.queryForObject(criteriaQuery, Keyword.class);
		return keyword;
	}
	
	@Override
	public void removeObjById(String id) {
		elasticsearchTemplate.delete(Keyword.class, id);
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
			qb.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.matchQuery("name", keyword)));
			//qb.must(queryStringQuery(" name:" + keyword));
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
	
	/**
	 * ES Aggregation(聚合)
	 * 	分两种:Metrics和Bucket
	 * 	还没完全理解，Metrics貌似囊括一些比较常见的聚合函数avg,sum,min,max等等……
	 * 	Bucket貌似比较侧重于对数据进行分组
	 */
	@Override
	public List<String> searchBrandIds(String keyword) {
		List<String> list = null;
		logger.info("keyword={}", keyword);
		
		String indexName = Keyword.class.getAnnotation(Document.class).indexName();
		String indexType = Keyword.class.getAnnotation(Document.class).type();
		SearchRequestBuilder reqBuilder = elasticsearchTemplate.getClient().prepareSearch(indexName).setTypes(indexType).setSearchType(SearchType.DEFAULT);
		
		reqBuilder.setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name", keyword)));
//		reqBuilder.addFields("brandId");
		reqBuilder.addAggregation(AggregationBuilders.terms("test").field("brandId"));
		
		SearchResponse resp = reqBuilder.execute().actionGet();
		
		org.elasticsearch.search.aggregations.bucket.terms.Terms a = resp.getAggregations().get("test");
		
		for(org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bk:a.getBuckets()){
			System.out.println("类型: "+bk.getKey()+"  分组统计数量 "+bk.getDocCount()+"  ");
		}
		
		return list;
	}
}
