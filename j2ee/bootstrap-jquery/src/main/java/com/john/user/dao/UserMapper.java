package com.john.user.dao;

import java.util.List;
import java.util.Map;

import com.john.user.vo.UserVo;

public interface UserMapper {
	List<UserVo> queryUsers();
	
	List<UserVo> findUsers(Map<String, Object> params);
}
