package com.john.user.service;

import java.util.List;

import com.john.user.vo.UserVo;

public interface UserService {
	public void mutiSave(List<UserVo> users);
	
	public void delUsers();
}