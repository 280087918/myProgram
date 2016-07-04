package com.john.user.service;

import java.util.List;

import com.john.user.vo.UserVo;

public interface UserService {
	void saveUser(UserVo userVo);
	
	public List<UserVo> listUser();
	
	public UserVo findUser(String id);
}
