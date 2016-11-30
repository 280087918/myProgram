package com.john.user.service;

import java.util.List;
import java.util.Map;

import com.john.user.vo.UserVo;

public interface UserService {
	public List<UserVo> listUser();
	
	public UserVo findUser(String id);
	
	/**
	 * 根据登录名和密码查找用户
	 * @param params
	 * @return
	 */
	public UserVo findLoginUser(Map<String, Object> params);
}
