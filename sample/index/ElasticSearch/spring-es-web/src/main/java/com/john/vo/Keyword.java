package com.john.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 测试索引的搜索，分词特性
 * @author zhang.hc
 * @date 2016年9月28日 下午2:25:25
 */
@Data
@ToString
@Document(indexName = "keyword", type = "local", shards = 5, replicas = 1)
@RequiredArgsConstructor
@NoArgsConstructor
public class Keyword {
	
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true)
	@NonNull
	private String id;
	
	@Field(type=FieldType.String, analyzer="ik_smart", searchAnalyzer="ik_smart", store=true)
	@NonNull
	private String name;
	
	@NonNull
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed, store=true)
	private String brandId;
	
	@Field(type=FieldType.String)
	private List<String> parentIds = new ArrayList<String>();
	
	public void addParentId(String pId) {
		if(!parentIds.contains(pId)) {
			parentIds.add(pId);
		}
	}
}
