package com.john.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.john.dao.MyScrollDao;
import com.john.vo.MyScroll;

@Service
public class MyScrollDaoImpl implements MyScrollDao {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private Client esClient;
	
	@PostConstruct
	public void init() {
		if(!elasticsearchTemplate.indexExists(MyScroll.class)) {
			elasticsearchTemplate.createIndex(MyScroll.class);
		}
		
		elasticsearchTemplate.putMapping(MyScroll.class);
	}
	
	@Override
	public void saveObject(MyScroll myScroll) {
		IndexQuery index = new IndexQueryBuilder().withId(myScroll.getId()).withObject(myScroll).build();
		String result = elasticsearchTemplate.index(index);
		log.info("存储结果:" + result);
	}
	
	@Override
	public List<MyScroll> searchList(String keyword) {
		log.info("查询开始......");
		if(StringUtils.isNotBlank(keyword)) {
			//获取文档的索引名称和类型
			String indexName = MyScroll.class.getAnnotation(Document.class).indexName();
			String indexType = MyScroll.class.getAnnotation(Document.class).type();
			
			//将要搜索的索引名称和类型传给SearchRequestBuilder
			SearchResponse scrollResponse = esClient.prepareSearch(indexName)
					.setTypes(indexType).setSearchType(SearchType.DEFAULT).setExplain(true)
					.addSort("count", SortOrder.ASC)
					.setSize(100).setScroll(TimeValue.timeValueMinutes(1))//这个size可以理解为每次取出多少条
					.execute().actionGet();
			
			//这个totalHits跟上面这个setSize没有关系。
			long count = scrollResponse.getHits().getTotalHits();
			System.out.println("命中:" + count);
			
			while(true) {
				for(SearchHit hit : scrollResponse.getHits()) {
					log.info("id:{}", hit.getSource().get("id"));
				}
				log.info("读取了:{}条数据", scrollResponse.getHits().hits().length);
				
				scrollResponse = esClient.prepareSearchScroll(scrollResponse.getScrollId())
						.setScroll(TimeValue.timeValueMinutes(5)).execute().actionGet();
				
				log.info("scrollId:{}", scrollResponse.getScrollId());
				
				if(scrollResponse.getHits().hits().length == 0) {
					log.info("读取完成.");
					break;
				}
			}
		}
		return null;
	}
}
