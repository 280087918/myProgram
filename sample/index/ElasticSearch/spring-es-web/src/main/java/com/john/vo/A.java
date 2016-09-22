package com.john.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@ToString
@Document(indexName = "a", type = "local")
public class A {
	
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true)
	private String id;
	
	//商品名称
	@Field(type = FieldType.String, store = true)
	private String name;
	
	@Field(type = FieldType.Nested)
	private List<B> bs = new ArrayList<B>();
	
	public A() {
		super();
	}
	
	public A(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void addB(B b) {
		if(!this.bs.contains(b)) {
			bs.add(b);
		}
	}
}
