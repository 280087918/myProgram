package com.john.vo;

import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName="car", type="cn")
public class Car {
	private String id;
	
	//品牌名称
	@Field
	private String brandName;
	
	//系列名称
	private String series;
	
	//生产日期
	private Date prodDate;
	
	//原产地
	private String areaType;
	
	//-----------about search---------------
	//搜索开始时间
	private Date prodDateBegin;
	
	//搜索结束时间
	private Date prodDateEnd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public Date getProdDate() {
		return prodDate;
	}

	public void setProdDate(Date prodDate) {
		this.prodDate = prodDate;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Date getProdDateBegin() {
		return prodDateBegin;
	}

	public void setProdDateBegin(Date prodDateBegin) {
		this.prodDateBegin = prodDateBegin;
	}

	public Date getProdDateEnd() {
		return prodDateEnd;
	}

	public void setProdDateEnd(Date prodDateEnd) {
		this.prodDateEnd = prodDateEnd;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brandName=" + brandName + ", series="
				+ series + ", prodDate=" + prodDate + ", areaType=" + areaType
				+ ", prodDateBegin=" + prodDateBegin + ", prodDateEnd="
				+ prodDateEnd + "]";
	}
}
