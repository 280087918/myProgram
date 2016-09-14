package com.john.dao.impl;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.john.dao.CommodityDao;
import com.john.vo.Commodity;

@Service
public class CommodityDaoImpl implements CommodityDao {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private Client esClient;
	
	@PostConstruct
	public void init() {
		if(!elasticsearchTemplate.indexExists(Commodity.class)) {
			elasticsearchTemplate.createIndex(Commodity.class);
		}
		
		elasticsearchTemplate.putMapping(Commodity.class);
	}
	
	@Override
	public void saveCommodity(Commodity commodity) {
		IndexQuery index = new IndexQueryBuilder().withId(commodity.getId()).withObject(commodity).build();
		String result = elasticsearchTemplate.index(index);
		log.info("存储结果:" + result);
	}
	
	@Override
	public void batchSaveCommodity(List<Commodity> commoditys) {
		if(CollectionUtils.isNotEmpty(commoditys)) {
			List<IndexQuery> indexs = new ArrayList<IndexQuery>();
			for(Commodity commodity : commoditys) {
				indexs.add(new IndexQueryBuilder().withId(commodity.getId()).withObject(commodity).build());
			}
			elasticsearchTemplate.bulkIndex(indexs);
		}
	}
	
	@Override
	public List<Commodity> searchList(String keyword) {
		List<Commodity> commoditys = null;
		
		if(StringUtils.isNotBlank(keyword)) {
			//获取文档的索引名称和类型
			String indexName = Commodity.class.getAnnotation(Document.class).indexName();
			String indexType = Commodity.class.getAnnotation(Document.class).type();
			
			//将要搜索的索引名称和类型传给SearchRequestBuilder
			SearchRequestBuilder reqBuilder = esClient.prepareSearch(indexName)
					.setTypes(indexType).setSearchType(SearchType.DEFAULT).setExplain(true);
			
			StringBuffer sb = new StringBuffer();
			sb.append(" name:" + keyword);
			
			QueryStringQueryBuilder queryBuilder = queryStringQuery(sb.toString()).analyzer("ik_smart");
			
			//设置高亮
			//reqBuilder.addHighlightedField("name").setHighlighterPreTags("<em>").setHighlighterPostTags("</em>");
			
			reqBuilder.setQuery(boolQuery().should(queryBuilder));
			
			SearchResponse resp = reqBuilder.execute().actionGet();
	        SearchHit[] hits = resp.getHits().getHits();
	        
	        //解析命中数据
	        if(ArrayUtils.isNotEmpty(hits)) {
	        	commoditys = new ArrayList<Commodity>();
	        	Commodity commodity = null;
	        	for(SearchHit searchHit : hits) {
	        		commodity = new Commodity();
	        		commodity.setId((String) searchHit.getSource().get("id"));
	        		commodity.setName((String) searchHit.getSource().get("name"));
	        		//commodity.setName(searchHit.getHighlightFields().get("name").fragments()[0].toString());
	        		commodity.setBrand((String) searchHit.getSource().get("brand"));
	        		commodity.setType((String) searchHit.getSource().get("type"));
	        		commodity.setSalesPrice((Double) searchHit.getSource().get("salesPrice"));
	        		commoditys.add(commodity);
	        	}
	        }
		}
		
		return commoditys;
	}
}
