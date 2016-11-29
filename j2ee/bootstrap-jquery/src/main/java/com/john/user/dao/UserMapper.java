package com.john.user.dao;

import java.util.List;

import com.john.user.vo.UserVo;

public interface UserMapper {
	List<UserVo> queryUsers();
	
	UserVo findById(String id);
}
