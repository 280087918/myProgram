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
@Document(indexName = "category", type = "local")
public class Category {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true)
	private String id;
	
	//商品名称
	@Field(type = FieldType.String, store = true)
	private String cName;
	
	public Category() {
		super();
	}
	
	public Category(String id, String cName) {
		this.id = id;
		this.cName = cName;
	}
}
