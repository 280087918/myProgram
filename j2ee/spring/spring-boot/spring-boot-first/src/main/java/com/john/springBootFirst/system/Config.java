package com.john.springBootFirst.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my")
public class Config {
	private List<String> servers = new ArrayList<String>();

	public List<String> getServers() {
		return this.servers;
	}
	
	private String name;
	
	//property name is 'user-age'
	private Integer userAge;
	
	//property name is 'user_password'
	private String userPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}

	@Override
	public String toString() {
		return "Config [servers=" + servers + ", name=" + name + ", userAge=" + userAge + ", userPassword="
				+ userPassword + "]";
	}
	
}
