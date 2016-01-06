package com.ysq.test.service;

import com.ysq.test.dao.UserDAO;
import com.ysq.test.entity.User;

public class UserService {
	private UserDAO userDao;

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public User login(String name, String password) {
		User user = findUser(name, password);
		if (user != null) {
			return addToken(user);
		}
		return null;
	}

	private User findUser(String name, String password) {
		return userDao.findUser(name, password);
	}

	private User addToken(User user) {
		return userDao.addToken(user);
	}
}
