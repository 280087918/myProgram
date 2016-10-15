package com.john.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import com.john.service.CommodityService;
import com.john.vo.Commodity;

@Service
public class CommodityServiceImpl extends BasicServiceImpl<Commodity> implements CommodityService {

	@Override
	@PostConstruct
	protected void init() {
		if(!elasticsearchTemplate.indexExists(Commodity.class)) {
			elasticsearchTemplate.createIndex(Commodity.class);
		}
	}
	
	@Override//这个不大好弄，先放一放
	public void updateBuyCountById(String id, Integer count) {
		//updateQuery准备
		UpdateQuery updateQuery = new UpdateQuery();
		updateQuery.setClazz(Commodity.class);
		updateQuery.setDoUpsert(false);//如果设置为true的话，此id的文档不存在则会创建一个新的文档出来
		updateQuery.setId(id);
		updateQuery.setIndexName(Commodity.class.getAnnotation(Document.class).indexName());
		updateQuery.setType(Commodity.class.getAnnotation(Document.class).type());
		
//		elasticsearchTemplate.update(query)
	}

}
