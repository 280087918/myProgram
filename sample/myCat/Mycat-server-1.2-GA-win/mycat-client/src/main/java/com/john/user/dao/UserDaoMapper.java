package com.john.user.dao;

import java.util.List;

import com.john.user.vo.UserVo;

public interface UserDaoMapper {
	public void saveUser(UserVo userVo);
	
	public List<UserVo> queryUsers();
	
	public void clearAll();
}
