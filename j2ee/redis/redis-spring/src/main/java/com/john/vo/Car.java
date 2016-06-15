package com.john.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Car implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private int year;
	
	private boolean isNew;
	
	public Car() {
		super();
	}
	
	public Car(String id, String name, int year, boolean isNew) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.isNew = isNew;
	}
}
