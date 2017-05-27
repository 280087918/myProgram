package com.john.vo;

import javax.validation.constraints.NotNull;

public class User {
	/** 用户名 **/
	private String userName;
	
	/** 邮箱 **/
	private String email;
	
	/** 登陆次数 **/
	private Integer LoginCount;

	@NotNull(message="名称不能为空")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLoginCount() {
		return LoginCount;
	}

	public void setLoginCount(Integer loginCount) {
		LoginCount = loginCount;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", email=" + email + ", LoginCount=" + LoginCount + "]";
	}
}
