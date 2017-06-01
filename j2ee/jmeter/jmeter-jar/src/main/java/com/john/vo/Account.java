package com.john.vo;

public class Account {
	private int account = 100000;
	
	public int decr(int decr) {
		account = account - decr;
		return account;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}
}
