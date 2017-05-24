package com.john.user.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserVo {
	
	private String id;
	
	private String name;
	
	private Integer age;
	
	public UserVo() {
		super();
	}
	
	public UserVo(String id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
}
