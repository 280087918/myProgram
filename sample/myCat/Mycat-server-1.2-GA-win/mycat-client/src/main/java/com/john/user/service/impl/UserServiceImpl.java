package com.john.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.john.user.dao.UserDaoMapper;
import com.john.user.service.UserService;
import com.john.user.vo.UserVo;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoMapper userDao;
	
	@Override
	public void mutiSave(List<UserVo> users) {
		for(UserVo user : users) {
			userDao.saveUser(user);
		}
	}
	
	@Override
	public void delUsers() {
		userDao.clearAll();
	}
}
