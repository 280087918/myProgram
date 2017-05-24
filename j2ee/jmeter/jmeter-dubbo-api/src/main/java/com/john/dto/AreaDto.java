package com.john.dto;

import java.io.Serializable;

public class AreaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6756325376633490728L;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "AreaDto [id=" + id + ", name=" + name + ", code=" + code + "]";
	}
}
