package com.john.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Document(indexName = "product", type = "local")
public class Product {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true)
	private String id;

	//商品名称
	@Field(type = FieldType.String, analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true)
	private String name;

	//品牌
	@Field(type = FieldType.String, analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true)
	private String brand;

	//售格
	@Field(type = FieldType.Double, index = FieldIndex.not_analyzed, store = true)
	private Double salesPrice;
	
	public Product() {}
	
	public Product(String id, String name, String brand, Double salesPrice) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.salesPrice = salesPrice;
	}
}
