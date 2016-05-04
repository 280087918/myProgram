package com.john.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Car {
	/** id **/
	private String id;
	
	/** 型号 **/
	private String model;
	
	/** 品牌 **/
	private String brand;
	
	/** 上市日期 **/
	private Date marketDate;
	
	public Car() {}
	public Car(String id, String model, String brand, Date marketDate) {
		this.id = id;
		this.model = model;
		this.brand = brand;
		this.marketDate = marketDate;
	}
}
