package com.john.user.service;

import java.util.List;

import com.john.user.vo.UserVo;

public interface UserService {
	public List<UserVo> listUser();
	
	public UserVo findUser(String id);
}
