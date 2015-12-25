package com.ysq.test.service;

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

}
