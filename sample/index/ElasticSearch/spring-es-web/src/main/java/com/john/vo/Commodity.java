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
@Document(indexName = "commodity", type = "local", shards = 5, replicas = 1)
public class Commodity {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true)
	private String id;

	//商品名称
	@Field(type = FieldType.String, analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true)
	private String name;

	//品牌
	@Field(type = FieldType.String, analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true)
	private String brand;
	
	//分类
	@Field(type = FieldType.String, analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true)
	private String type;

	//售价
	@Field(type = FieldType.Double, index = FieldIndex.not_analyzed, store = true)
	private Double salesPrice;
	
	public Commodity() {}
	
	public Commodity(String id, String name, String brand, String type, Double salesPrice) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.type = type;
		this.salesPrice = salesPrice;
	}
}
