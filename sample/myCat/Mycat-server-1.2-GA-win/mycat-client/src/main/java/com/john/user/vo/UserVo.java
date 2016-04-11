package com.john.user.vo;

import java.util.Date;

/**
 * 
 * @author zhang.hc
 * @date 2016年4月11日 下午2:15:32
 */
public class UserVo {
	//id
	private Integer userId;
	
	//收货地址
	private String receiveAddress;
	
	//收货时间
	private Date dateTime;
	
	//省份编码
	private String provinceCode;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Override
	public String toString() {
		return "UserVo [userId=" + userId + ", receiveAddress="
				+ receiveAddress + ", dateTime=" + dateTime + ", provinceCode="
				+ provinceCode + "]";
	}
}
