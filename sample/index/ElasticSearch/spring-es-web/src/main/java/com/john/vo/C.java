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
@Document(indexName = "c", type = "local")
public class C {
	
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true)
	private String id;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	private String name;
	
	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
	private Integer age;
	
	public C() {
		super();
	}
	
	public C(String id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
}
