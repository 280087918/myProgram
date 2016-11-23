package com.john.dto;

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
 * 模拟ffzx生产的场景，用以解决实际问题
 * @author zhang.hc
 * @date 2016年10月11日 下午5:08:51
 */
@ToString
@Document(indexName="cims", type="commodity")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Commodity {
	@Id
	@Field(index=FieldIndex.not_analyzed)
	@NonNull
	private String goodsId;
	
	/**
	 * 商品名称
	 */
	@Field(type=FieldType.String, analyzer="ik_smart", searchAnalyzer="ik_smart")
	@NonNull
	private String name;
	
	/**
	 * 已购买数量
	 */
	@Field(type=FieldType.Integer)
	@NonNull
	private Integer buyCount;
}
