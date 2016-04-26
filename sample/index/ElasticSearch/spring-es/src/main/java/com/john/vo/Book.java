package com.john.vo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 书本
 * 主要测试对文本的搜索，这里主要针对name进行搜索
 * @author zhang.hc
 * @date 2016年4月25日 下午4:08:15
 */
@Data
@ToString
@Document(indexName="book", type="tech")
public class Book {
	//id
	@Id
	@Field(index=FieldIndex.not_analyzed, store=true)
	private String id;
	
	//书名
	@Field(type=FieldType.String, analyzer="ik_smart", searchAnalyzer="ik_smart", store=true)
	private String name;
	
	//编码
	@Field(type=FieldType.String, analyzer="ik_smart", searchAnalyzer="ik_smart", store=true)
	private String code;
	
	//发行日
	@Field(type=FieldType.Date, index=FieldIndex.not_analyzed, store=true)
	private Date publishDate;
	
	public Book() {}

	public Book(String id, String name, String code, Date publishDate) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.publishDate = publishDate;
	}
}
