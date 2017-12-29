package com.john.user.vo;

import lombok.Data;
import lombok.ToString;

//@Data
//@ToString
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
}
