package com.john.springBootFirst.user.service.impl;

import org.springframework.stereotype.Service;

import com.john.springBootFirst.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public void addUser() {
		System.out.println("a user added......");
	}
	
}
