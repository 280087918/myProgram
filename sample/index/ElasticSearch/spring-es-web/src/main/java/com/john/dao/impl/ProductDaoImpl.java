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
import org.springframework.stereotype.Repository;

import com.john.dao.ProductDao;
import com.john.vo.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private Client esClient;
	
	@PostConstruct
	public void init() {
		if(!elasticsearchTemplate.indexExists(Product.class)) {
			elasticsearchTemplate.createIndex(Product.class);
		}
		
		elasticsearchTemplate.putMapping(Product.class);
	}
	
	@Override
	public void saveProduct(Product product) {
		IndexQuery index = new IndexQueryBuilder().withId(product.getId()).withObject(product).build();
		String result = elasticsearchTemplate.index(index);
		log.info("存储结果:" + result);
	}
	
	@Override
	public void batchSaveProduct(List<Product> products) {
		if(CollectionUtils.isNotEmpty(products)) {
			List<IndexQuery> indexs = new ArrayList<IndexQuery>();
			for(Product product : products) {
				indexs.add(new IndexQueryBuilder().withId(product.getId()).withObject(product).build());
			}
			elasticsearchTemplate.bulkIndex(indexs);
		}
	}
	
	@Override
	public List<String> searchTips(String keyword) {
		List<String> tips = null;
		if(StringUtils.isNotBlank(keyword)) {
			//获取文档的索引名称和类型
			String indexName = Product.class.getAnnotation(Document.class).indexName();
			String indexType = Product.class.getAnnotation(Document.class).type();
			
			//将要搜索的索引名称和类型传给SearchRequestBuilder
			SearchRequestBuilder reqBuilder = esClient.prepareSearch(indexName)
					.setTypes(indexType).setSearchType(SearchType.DEFAULT).setExplain(true);
			
			StringBuffer sb = new StringBuffer();
			sb.append(" +name:" + keyword);
			
			QueryStringQueryBuilder queryBuilder = queryStringQuery(sb.toString()).analyzer("ik_smart");
			
			//设置高亮
			//reqBuilder.addHighlightedField("name").setHighlighterPreTags("<strong>").setHighlighterPostTags("</strong>");
			reqBuilder.addHighlightedField("name");
			
			reqBuilder.setQuery(boolQuery().should(queryBuilder));
			reqBuilder.setFrom(0).setSize(5);//限制返回条数
			
			SearchResponse resp = reqBuilder.execute().actionGet();
	        SearchHit[] hits = resp.getHits().getHits();
	        
	        //解析命中数据
	        if(ArrayUtils.isNotEmpty(hits)) {
	        	tips = new ArrayList<String>();
	        	for(SearchHit searchHit : hits) {
	        		tips.add("" + searchHit.getHighlightFields().get("name").fragments()[0].toString());
	        	}
	        }
		}
		return tips;
	}
	
	@Override
	public List<Product> searchList(String keyword) {
		List<Product> products = null;
		
		if(StringUtils.isNotBlank(keyword)) {
			//获取文档的索引名称和类型
			String indexName = Product.class.getAnnotation(Document.class).indexName();
			String indexType = Product.class.getAnnotation(Document.class).type();
			
			//将要搜索的索引名称和类型传给SearchRequestBuilder
			SearchRequestBuilder reqBuilder = esClient.prepareSearch(indexName)
					.setTypes(indexType).setSearchType(SearchType.DEFAULT).setExplain(true);
			
			StringBuffer sb = new StringBuffer();
			sb.append(" +name:" + keyword);
			
			QueryStringQueryBuilder queryBuilder = queryStringQuery(sb.toString()).analyzer("ik_smart");
			
			//设置高亮
			reqBuilder.addHighlightedField("name").setHighlighterPreTags("<em>").setHighlighterPostTags("</em>");
			
			reqBuilder.setQuery(boolQuery().should(queryBuilder));
			
			SearchResponse resp = reqBuilder.execute().actionGet();
	        SearchHit[] hits = resp.getHits().getHits();
	        
	        //解析命中数据
	        if(ArrayUtils.isNotEmpty(hits)) {
	        	products = new ArrayList<Product>();
	        	Product product = null;
	        	for(SearchHit searchHit : hits) {
	        		product = new Product();
	        		product.setId((String) searchHit.getSource().get("id"));
	        		product.setName(searchHit.getHighlightFields().get("name").fragments()[0].toString());
	        		product.setBrand((String) searchHit.getSource().get("brand"));
	        		product.setSalesPrice((Double) searchHit.getSource().get("salesPrice"));
	        		products.add(product);
	        	}
	        }
		}
		
		return products;
	}
}
