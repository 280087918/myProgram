package com.john.vo;

import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@ToString
@Document(indexName = "my_scroll", type = "local")
public class MyScroll {
	
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true)
	private String id;
	
	//商品名称
	@Field(type = FieldType.String, analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true)
	private String name;
	
	//售价
	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
	private Integer count;
	
	public MyScroll() {
		super();
	}
	
	public MyScroll(String id, String name, Integer count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}
}
