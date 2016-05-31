package com.john.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AreaDto implements Serializable {

	/****/
	private static final long serialVersionUID = 4686898597405571778L;

	/** id **/
	private String id;
	
	/** 区域名称 **/
	private String name;
	
	/** 区域编码 **/
	private String code;
	
	public AreaDto(){
		super();
	}
	
	public AreaDto(String id, String name, String code) {
		this.id = id;
		this.name = name;
		this.code = code;
	}
}
