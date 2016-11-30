package com.john.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.user.dao.UserMapper;
import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userDao;
	
	@Override
	public List<UserVo> listUser() {
		return userDao.queryUsers();
	}
	
	@Override
	public UserVo findUser(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<UserVo> users = userDao.findUsers(params);
		if(CollectionUtils.isNotEmpty(users)) {
			if(users.size() == 1) {
				return users.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public UserVo findLoginUser(Map<String, Object> params) {
		params.put("name", params.get("name"));
		params.put("password", params.get("password"));
		List<UserVo> users = userDao.findUsers(params);
		if(CollectionUtils.isNotEmpty(users)) {
			if(users.size() == 1) {
				return users.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
