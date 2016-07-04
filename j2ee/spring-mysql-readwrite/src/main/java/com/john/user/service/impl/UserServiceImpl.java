package com.john.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.john.user.dao.UserMapper;
import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userDao;

	@Override
	@Transactional
	public void saveUser(UserVo userVo) {
		userDao.saveUser(userVo);
	}
	
	@Override
	public List<UserVo> listUser() {
		return userDao.queryUsers();
	}
	
	@Override
	public UserVo findUser(String id) {
		return userDao.findById(id);
	}
}
