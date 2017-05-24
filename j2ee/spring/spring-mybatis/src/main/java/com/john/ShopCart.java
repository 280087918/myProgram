package com.john;

import org.springframework.stereotype.Component;

/**
 * 这个用来测试spring环境的
 * @author zhang.hc
 * @date 2016年4月8日 上午9:31:48
 */
@Component("shopCart")
public class ShopCart {
	private Integer productNo;
	
	private Integer amount;

	public Integer getProductNo() {
		return productNo;
	}

	public void setProductNo(Integer productNo) {
		this.productNo = productNo;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
