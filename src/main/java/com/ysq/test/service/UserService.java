package com.ysq.test.service;

import com.alibaba.fastjson.JSON;
import com.ysq.test.dao.UserDAO;

public class UserService {
	private UserDAO userDao;
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public int userCount(){
		return userDao.getAllUser().size();
	}

	public String userListToJson() {
		return JSON.toJSONString(userDao.getAllUser());
	}
	
	public boolean loginSuccess() {
		return userDao.getAllUser().size() > 0;
	}
	
	public boolean login(String name, String password) {
		return userDao.login(name, password);
	}
}
