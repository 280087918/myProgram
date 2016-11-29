package com.john.user.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserVo {
	
	private Integer id;
	
	private String name;
	
	private String password;
}
