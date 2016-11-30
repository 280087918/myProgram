package com.john.system.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ResultVo {
	/**
	 * SUCCESS
	 * ERROR
	 */
	private String status;
	
	private String jsonResult;
}
