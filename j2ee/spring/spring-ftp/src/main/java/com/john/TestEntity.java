package com.john;

import lombok.Data;
import lombok.ToString;

import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class TestEntity {
	private Integer id = 1;
	
	private String name = "test";
}
