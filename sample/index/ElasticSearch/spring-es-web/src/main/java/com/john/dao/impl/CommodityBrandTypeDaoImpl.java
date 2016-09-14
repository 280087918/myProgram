package com.john.dao.impl;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
//import static org.elasticsearch.index.query.QueryBuilders.termsQuery;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.john.dao.CommodityBrandTypeDao;
import com.john.vo.CommodityBrandType;

@Service
public class CommodityBrandTypeDaoImpl implements CommodityBrandTypeDao {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private Client esClient;
	
	@PostConstruct
	public void init() {
		if(!elasticsearchTemplate.indexExists(CommodityBrandType.class)) {
			elasticsearchTemplate.createIndex(CommodityBrandType.class);
		}
		
		elasticsearchTemplate.putMapping(CommodityBrandType.class);
	}
	
	@Override
	public void saveBrandType(CommodityBrandType commodityBrandType) {
		IndexQuery index = new IndexQueryBuilder().withId(commodityBrandType.getId()).withObject(commodityBrandType).build();
		String result = elasticsearchTemplate.index(index);
		log.info("存储结果:" + result);
	}
	
	@Override
	public List<CommodityBrandType> searchListByBrandAndType(String brand, String type) {
		List<CommodityBrandType> brandTypes = null;
		
		if(StringUtils.isNotBlank(brand) && StringUtils.isNotBlank(type)) {
			//获取文档的索引名称和类型
			String indexName = CommodityBrandType.class.getAnnotation(Document.class).indexName();
			String indexType = CommodityBrandType.class.getAnnotation(Document.class).type();
			
			//将要搜索的索引名称和类型传给SearchRequestBuilder
			SearchRequestBuilder reqBuilder = esClient.prepareSearch(indexName)
					.setTypes(indexType).setSearchType(SearchType.DEFAULT).setExplain(true);
			
			StringBuffer sb = new StringBuffer();
			sb.append(" +brand:" + brand + " +type:" + type);
			
			QueryStringQueryBuilder queryBuilder = queryStringQuery(sb.toString());
//			TermsQueryBuilder brandTermsQuery = termsQuery("brand", brand); 
//			TermsQueryBuilder typeTermsQuery = termsQuery("type", type);
			
			reqBuilder.setQuery(boolQuery().should(queryBuilder));
			
			SearchResponse resp = reqBuilder.execute().actionGet();
	        SearchHit[] hits = resp.getHits().getHits();
	        
	        //解析命中数据
	        if(ArrayUtils.isNotEmpty(hits)) {
	        	brandTypes = new ArrayList<CommodityBrandType>();
	        	CommodityBrandType brandType = null;
	        	for(SearchHit searchHit : hits) {
	        		brandType = new CommodityBrandType();
	        		brandType.setId((String) searchHit.getSource().get("id"));
	        		brandType.setBrand((String) searchHit.getSource().get("brand"));
	        		brandType.setType((String) searchHit.getSource().get("type"));
	        		brandTypes.add(brandType);
	        	}
	        }
		}
		
		return brandTypes;
	}
	
	@Override
	public List<CommodityBrandType> searchCriteria(String brand, String type) {
		List<CommodityBrandType> list = null;
		
		Criteria criteria = Criteria.where("brand").is(brand).and("type").is(type);
		
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
		
		list = elasticsearchTemplate.queryForList(criteriaQuery, CommodityBrandType.class);
		return list;
	}
	
	public List<CommodityBrandType> searchQueryString(String brand, String type) {
		List<CommodityBrandType> list = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append(" +brand:" + brand + "+type:" + type);
		
		return list;
	}
}
