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
@Document(indexName = "commodity_brand_type", type = "local")
public class CommodityBrandType {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true)
	private String id;
	
	//品牌
	@Field(type = FieldType.String, analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true)
	private String brand;
	
	//分类
	@Field(type = FieldType.String, analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true)
	private String type;
	
	public CommodityBrandType() {
	}
	
	public CommodityBrandType(String id, String brand, String type) {
		this.id = id;
		this.brand = brand;
		this.type = type;
	}
}
