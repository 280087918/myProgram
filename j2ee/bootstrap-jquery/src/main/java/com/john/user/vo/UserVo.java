package com.john.user.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 64868578133314602L;

	private String id;
	
	private String name;
	
	private String password;
}
