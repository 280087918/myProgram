package com.john.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.john.dto.Commodity;
import com.john.service.CommodityService;

/**
 * minimumShouldMatch("75%"):搜索条件里面至少匹配75%，比如搜索"美的空调一匹",ik_smart会拆成三个词
 * 	
 * @author zhang.hc
 * @date 2016年11月23日 下午4:28:57
 */
@Service
public class CommodityServiceImpl extends BasicServiceImpl<Commodity> implements CommodityService {

	@Override
	@PostConstruct
	protected void init() {
		checkAndCreate(Commodity.class);
	}
	
	public List<Commodity> searchList(Map<String, Object> params) {
		logger.info("搜索入参:{}", params);
		//result
		List<Commodity> resultList = null;
		
		//搜索关键词
		String keywords = params.get("keywords") == null ? "" : (String) params.get("keywords");
		
		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		
		if(StringUtils.isNotBlank(keywords)) {
			keywords = keywords.trim();
			qb.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.matchQuery("name", keywords).minimumShouldMatch("75%")));
		}
		
		searchQuery.withQuery(qb);
		
		resultList = elasticsearchTemplate.queryForList(searchQuery.build(), Commodity.class);
		
		return resultList;
	}
}
