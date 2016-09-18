package com.ffzx.dto;

import java.io.Serializable;


public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1824414079104860490L;
	

	private String name;
	
	private Integer age;
	
	
	public Message() {
		super();
	}
	
	public Message(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Message [name=" + name + ", age=" + age + "]";
	}
}
