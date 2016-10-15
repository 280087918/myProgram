package com.john.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;
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
	@Field(index=FieldIndex.not_analyzed, store=true)
	@NonNull
	private String goodsId;
	
	/**
	 * 商品名称
	 */
	@Field(type=FieldType.String, analyzer="ik_smart", searchAnalyzer="ik_smart", store=true)
	@NonNull
	private String name;
	
	/**
	 * 已购买数量 order_db.summary_sales.sale_num
	 */
	@Field(type=FieldType.Integer, index=FieldIndex.not_analyzed, store=true)
	@NonNull
	private Integer buyCount;
	
	/**
	 * 商品分类父id
	 */
	@Field(type = FieldType.String)
	private List<String> cparentIds = new ArrayList<String>();
	
	//添加分类id
	public void addCParendIds(String cid) {
		if(StringUtils.isNotBlank(cid) && !cparentIds.contains(cid)) {
			cparentIds.add(cid);
		}
	}
}
