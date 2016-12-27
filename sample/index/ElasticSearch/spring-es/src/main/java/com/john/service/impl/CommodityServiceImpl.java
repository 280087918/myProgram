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
 * trick(1):minimumShouldMatch("75%"):搜索条件里面至少匹配75%，比如搜索"美的空调一匹",ik_smart会拆成三个词(美的、空调、一匹)
 * 	可以理解为三个搜索条件的75%匹配。3*75%=2.25。向下取整=2。股需要匹配的字段中只要包含两个或以上的词就可以搜索出来。
 * 
 * trick(2):operator(Operator.AND):拆分的词以何种方式进行条件组装查询，一般默认都是OR只要有一个条件满足即可。AND意思就是拆分出来的词都要满足才能匹配出来
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
		
		//库存搜索
		String stockCode = params.get("stockCode") == null ? "" : (String) params.get("stockCode");
		
		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		
		if(StringUtils.isNotBlank(keywords)) {
			keywords = keywords.trim();
			qb.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.matchQuery("name", keywords).minimumShouldMatch("75%")));
		}
		
		if(StringUtils.isNotBlank(stockCode)){
			qb.must(QueryBuilders.matchQuery("stock", stockCode));
		}
		
		searchQuery.withQuery(qb);
		
		resultList = elasticsearchTemplate.queryForList(searchQuery.build(), Commodity.class);
		
		return resultList;
	}
}
