package com.john.tx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TxDto {
	private Integer id;
	
	private Integer num;
	
	//更新的版本号。
	private Integer version;
}
